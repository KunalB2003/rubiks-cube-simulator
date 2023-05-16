window.onload = () => {
    const body = document.querySelector('body');
    let faces = document.createElement('div');
    faces.id = 'faces';
    let facesArr = [];
    let order = [1, 0, 4, 5, 2, 3];
    body.appendChild(faces);
    const buttonsPerFace = 9;

    for (let i = 0; i < order.length; i++) {
        let face = document.createElement('div');
        face.className = 'face';
        for (let j = 0; j < buttonsPerFace; j++) {
            let button = document.createElement('button');
            button.innerText = order[i];
            button.addEventListener('click', () => {
                let int = parseInt(button.innerText);
                button.innerText = (int + 1) % 6;
            });
            face.appendChild(button);
        }
        facesArr.push(face);
        faces.appendChild(face);
    }
    bottomtops(5, true);
    bottomtops(4, false);
    function bottomtops(index, bottom) {
        faces.removeChild(facesArr[index]);
        facesArr[index].style.position = 'absolute';
        facesArr[index].style.left = "6vw";
        if (bottom) {
            facesArr[index].style.top = "12vw";
        } else {
            facesArr[index].style.top = "0";
        }
        body.appendChild(facesArr[index]);
    }

}