/** Fetches hex colors from the server and adds them to the DOM. */
function loadGrid() {
    fetch('/color-grid').then(response => response.json()).then((pixelsList) => {
        const gameArea = document.getElementById("grid");
        var gridWidth = pixelsList[pixelsList.length].xAxis,
            gridHeight = pixelsList[pixelsList.length].yAxis;

        var index = 0;

        const trElement = document.createElement('tr');
        pixelsList.forEach((pixel) => {
            if(index == 0){
                const tdElement = document.createElement('td');
                createButtonElement(pixel);
                tdElement.appendChild(buttonElement);
            }
            else if(index == gridWidth){
                createButtonElement(pixel);
                tdElement.appendChild(buttonElement);
                trElement.appendChild(tdElement);
                index = 0;
            }
            else{
                createButtonElement(pixel);
                tdElement.appendChild(buttonElement);
            }
            console.log(pixel);
        })

        // Load all buttons into a table
        gameArea.appendChild(trElement);
    });
}

var buttonID = 1;

/** Creates an element that represents a button */
function createButtonElement(pixel) {
    const buttonElement = document.createElement('button');
    buttonElement.className = 'gameButton';
    buttonElement.id = 'gameButton' + buttonID;
    buttonElement.onclick = function(){
        setColor(this.id);
    };
    buttonID++;
    
    buttonElement.innerText = pixel.hexColor;
    return buttonElement;
}

