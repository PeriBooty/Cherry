function openNav() {
    document.getElementById("navbar").style.width = "250px";
    // document.getElementById("main").style.marginLeft = "250px";
    document.body.style.backgroundColor = "rgba(0,0,0,0.6)";
	console.log("clicked open");
}

function closeNav() {
    document.getElementById("navbar").style.width = "0";
    // document.getElementById("main").style.marginLeft = "0";
    document.body.style.backgroundColor = "white";
	console.log("clicked close");
}
