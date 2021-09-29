var oReq = new XMLHttpRequest();
oReq.addEventListener("load", reqListener);
oReq.open("GET", "/tdb-cross-charges/authorizeServlet");
oReq.send();

function reqListener () {
    if (this.responseText === 'false'){
        window.location.href = "/Logout.do";
    }
}