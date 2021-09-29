package com.brandmaker.cs.skyhigh.tdb.webapi.helpers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JacksonCustomProvider extends JacksonJaxbJsonProvider {

    private final SimpleModule module = new SimpleModule("mapl-dse-dimension-sync",
            new Version(1,0,2, "", "com.brandmaker.cs.skyhigh.accenture", "mapl-dse-dimension-sync"));
    private final ObjectMapper mapper;
    private static final JacksonCustomProvider INSTANCE = new JacksonCustomProvider();

    public JacksonCustomProvider() {
        super();
        this.mapper = _mapperConfig.getConfiguredMapper() != null
                ? _mapperConfig.getConfiguredMapper() : _mapperConfig.getDefaultMapper();

        configure(mapper);
        register(mapper);
    }

    private void configure(final ObjectMapper mapper) {
        // not default
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
        mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        mapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        mapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);

        // defaults
        mapper.disable(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS);
        mapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
    }

    private void register(final ObjectMapper mapper) {
        mapper.registerModule(module);
        mapper.registerModule(new JavaTimeModule());
        addMoneySerializer();
        addDateSerializer();
        addDateDeserializer();
    }

    private void addMoneySerializer() {
        this.addSerializer((BigDecimal value) ->
                new DecimalFormat("#0.##").format(value), BigDecimal.class);
    }

    private void addDateSerializer() {
        this.addSerializer(MaplUtils::formatDate, Date.class);
    }

    private void addDateDeserializer() {
        // MaPl date alignment apply if server time is not UTC
        if (!ZoneId.of("UTC").equals(ZoneId.systemDefault())) {
            this.addDeserializer((Date value) -> {
                final String result = MaplUtils.formatDateToUTC(value);
                return MaplUtils.parseDate(result);
            }, Date.class, Date.class);
        }
    }

    private <T> void addSerializer(final Function<T, String> transformer, final Class<T> type) throws IllegalStateException {
        module.addSerializer(type, new JsonSerializer<T>() {
            @Override
            public void serialize(final T value, final JsonGenerator jgen, final SerializerProvider serializers) {
                try {
                    jgen.writeString(transformer.apply(value));
                } catch (IOException e) {
                    throw new IllegalStateException("Could not stringify with a transformer: " + value
                            + ". Reason: " + e.getMessage());
                }
            }
        });
    }

    private <T, R> void addDeserializer(final Function<T, R> transformer, final Class<T> inputType,
                                        final Class<R> returnType) throws IllegalStateException {
        module.addDeserializer(returnType, new JsonDeserializer<R>() {
            @Override
            public R deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext) {
                try {
                    final T value = jsonParser.getCodec().readValue(jsonParser, inputType);
                    return transformer.apply(value);
                } catch (IOException e) {
                    throw new IllegalStateException("Could not transform with a transformer type: " + inputType
                            + ". Reason: " + e.getMessage());
                }
            }
        });
    }

    public ObjectMapper objectMapper() {
        return this.mapper;
    }

    public static JacksonCustomProvider instance() {
        return INSTANCE;
    }
}
