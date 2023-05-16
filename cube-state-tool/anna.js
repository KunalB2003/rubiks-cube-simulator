window.onload = () => {
    const body = document.querySelector('body');
    let cube = document.createElement('div');
    cube.id = 'cube';
    let faces = document.createElement('div');
    faces.id = 'faces';
    let facesArr = [];
    let colorsOrder = ["green","orange", "white", "yellow", "red", "blue"];
    let order = [1, 0, 4, 5, 2, 3];
    body.appendChild(faces);
    const buttonsPerFace = 9;

    for (let i = 0; i < order.length; i++) {
        let face = document.createElement('div');
        face.className = 'face';
        for (let j = 0; j < buttonsPerFace; j++) {
            let button = document.createElement('button');
            button.style.backgroundColor = colorsOrder[order[i]];
            button.innerText = order[i];
            button.addEventListener('click', () => {
                let int = colorsOrder.indexOf(button.style.backgroundColor);
                let newNum = (int + 1) % 6;
                button.style.backgroundColor = colorsOrder[newNum];
                button.innerText = newNum;
            });
            face.appendChild(button);
        }
        facesArr.push(face);
        faces.appendChild(face);
    }
    cube.appendChild(faces);
    bottomtops(order.length - 1, true);
    bottomtops(order.length - 2, false);
    function bottomtops(index, bottom) {
        faces.removeChild(facesArr[index]);
        facesArr[index].style.position = 'absolute';
        facesArr[index].style.left = "12vw";
        if (bottom) {
            facesArr[index].style.top = "24vw";
        } else {
            facesArr[index].style.top = "0";
        }
        cube.appendChild(facesArr[index]);
    }
    body.appendChild(cube);
}