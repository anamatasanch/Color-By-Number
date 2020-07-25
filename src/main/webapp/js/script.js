let tiles = new Map();
let correct = new Map();

function getActive(){
    var header = document.getElementById("palette");
    var paletteHolder = header.getElementsByClassName("palette-button");
    for(var i=0; i < paletteHolder.length; i++){
        paletteHolder[i].addEventListener("click", function() {
            var current = document.getElementsByClassName("active-button");
            current[0].className = current[0].className.replace(" active-button", "");
            this.className += " active-button";
        });
    }
    console.log(tiles);
}


function setColor(gameButton) {
    var property = document.getElementById(gameButton);
    var x = document.getElementById("palette");
    var activeColor = x.getElementsByClassName("active-button");
    property.style.backgroundColor = activeColor[0].style.backgroundColor;
    console.log("It was colored to ", hexc(activeColor[0].style.backgroundColor));
    console.log("Correct color was ", correct.get(gameButton));
    if(hexc(activeColor[0].style.backgroundColor) == correct.get(gameButton)){
      tiles.delete(gameButton);
    }else if (!tiles.has(gameButton)){
      tiles.set(gameButton, correct.get(gameButton));
    }
}

// as discussed by Hussein's answer
// https://stackoverflow.com/questions/5999209/how-to-get-the-background-color-code-of-an-element-in-hex
function hexc(colorval) {
  var parts = colorval.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
  delete(parts[0]);
  for (var i = 1; i <= 3; ++i) {
    parts[i] = parseInt(parts[i]).toString(16);
    if (parts[i].length == 1) parts[i] = '0' + parts[i];
  }
  color = '#' + parts.join('');
  return color;
}

async function getColors() {
    const response = await fetch('/UploadServlet');
    const colors = await response.json();
    tiles = new Map();
    correct = new Map();

    let colorMap = createPalette(colors);
    createGrid(colors, colorMap);
    getActive();
}

function createPalette(colors){
    let count = 1;
    let colorMap = new Map();

    for (i = 0; i < colors.length; i++) {
        if (i===0) {
            let btnElement = document.createElement('button');
            btnElement.innerText = count;
            btnElement.classList.add('palette-button');
            btnElement.classList.add('active-button');
            btnElement.setAttribute("id", count+'p');
            btnElement.setAttribute("style", "background-color: "+colors[i]+";");
            let palletteDiv = document.getElementById("palette");
            palletteDiv.appendChild(btnElement);

            colorMap.set(colors[i], count);
            count++;
        }else if (!colorMap.has(colors[i])) {
            let btnElement = document.createElement('button');
            btnElement.innerText = count;
            btnElement.classList.add('palette-button');
            btnElement.setAttribute("id", count+'p');
            btnElement.setAttribute("style", "background-color: "+colors[i]+";");
            let palletteDiv = document.getElementById("palette");
            palletteDiv.appendChild(btnElement);

            colorMap.set(colors[i], count);
            count++;
        }
    }
    return colorMap;
}

/** Creates <tr> and <td> elements containing the game buttons. */
function createGrid(colors, colorMap) {
    let trElement = document.createElement('tr');
    let gridDiv = document.getElementById("grid");

    for (i = 0; i < colors.length; i++) {
        if (i%16===0) {
            trElement = document.createElement('tr');
        }
        gridDiv.appendChild(trElement);
        let tdElement = document.createElement('td');
        trElement.appendChild(tdElement);
        let btnElement = document.createElement('button');
        btnElement.classList.add('gameButton');
        btnElement.onclick = function() { setColor(this.id); };
        btnElement.setAttribute("id", i+'c');
        btnElement.innerText = colorMap.get(colors[i]);
        tdElement.appendChild(btnElement);
        tiles.set(i+'c', colors[i]);
        correct.set(i+'c', colors[i]);
    }
}

function checkGrid(){
  if (tiles.size === 0){
    correctPopUp();
  }else{
    tryAgainPopUp()
  }
}

function correctPopUp(){
    console.log("gotcha");
    alert("Correct!");
}

function tryAgainPopUp(){
    console.log("gotcha");
    alert("Sorry, try again!");
}

function customCursor() {
    const cursor = document.querySelector('.cursor');

    document.addEventListener('mousemove', e => {
        cursor.setAttribute("style", "top: "+(e.pageY-10)+"px; left: "+(e.pageX-10)+"px;")
    })

    document.addEventListener('click', () => {
        cursor.classList.add("expand");

        setTimeout(() => {
            cursor.classList.remove("expand")
        }, 500)
    })
}
