function getActive(){
    var header = document.getElementById("palette");
        var paletteHolder = header.getElementsByClassName("palette-button");
        for(var i=0; i < paletteHolder.length; i++){
        paletteHolder[i].addEventListener("click", function() {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        this.className += " active";
        console.log(current);
            });
        }
}


function setColor(gameButton) {
    var property = document.getElementById(gameButton);
    var x = document.getElementById("palette");
    var activeColor = x.getElementsByClassName("active");
    console.log(activeColor);
    console.log(activeColor[0].style.backgroundColor);
    property.style.backgroundColor = activeColor[0].style.backgroundColor;
}




