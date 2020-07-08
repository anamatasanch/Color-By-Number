var count = 1;
function setColor(gameButton) {
    var property = document.getElementById(gameButton);
    if (count == 0) {
        property.style.backgroundColor = "#868686"
        count = 1;        
    }
    else {
        property.style.backgroundColor = "#C70039"
        count = 0;
    }
}
