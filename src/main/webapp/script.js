function getActive(){
    var header = document.getElementById("palette");
        var paletteHolder = header.getElementsByClassName("palette-button");
        for(var i=0; i < paletteHolder.length; i++){
        paletteHolder[i].addEventListener("click", function() {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";
            });
        }
}



