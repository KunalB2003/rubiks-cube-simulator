window.onload = () => {

    var states = [];

    for (let i = 0; i < 6; i++) {
        states.push([]);
        for (let j = 0; j < 8; j++) {
            states[i][j] = i;
        }
    }

    let c1;
    let c2;

    for (let i = 0; i < 3; i++) {
        for (let i2 = 0; i2 < 3; i2++) {

            for (let j = 0; j < 4; j++) {
                for (let j2 = 0; j2 < 3; j2++) {
                    let e = document.createElement("button");
                    document.body.appendChild(e);

                    let text = "!";

                    if ((i == 0 || i == 2) && j != 1) {
                        e.style.cssText = "visibility:hidden";
                    } else {
                        if (i == 0 && j == 1) {
                            text = 2;
                            c1 = "a2";
                        } else if (i == 1 && j == 0) {
                            text = 1;
                            c1 = "a1";
                        } else if (i == 1 && j == 1) {
                            text = 0;
                            c1 = "a0";
                        } else if (i == 1 && j == 2) {
                            text = 4;
                            c1 = "a4";
                        } else if (i == 1 && j == 3) {
                            text = 5;
                            c1 = "a5";
                        } else if (i == 2 && j == 1) {
                            text = 3;
                            c1 = "a3";
                        }
                        switch (true) {
                            case (i2 == 0 && j2 == 0):
                                c2 = "b0";
                                break;
                            case (i2 == 0 && j2 == 1):
                                c2 = "b1";
                                break;
                            case (i2 == 0 && j2 == 2):
                                c2 = "b2";
                                break;
                            case (i2 == 1 && j2 == 0):
                                c2 = "b7";
                                break;
                            case (i2 == 1 && j2 == 1):
                                c2 = "b8";
                                break;
                            case (i2 == 1 && j2 == 2):
                                c2 = "b3";
                                break;
                            case (i2 == 2 && j2 == 0):
                                c2 = "b6";
                                break;
                            case (i2 == 2 && j2 == 1):
                                c2 = "b5";
                                break;
                            case (i2 == 2 && j2 == 2):
                                c2 = "b4";
                                break;
                        }
                    }
                    e.innerHTML = text;
                    e.addEventListener("click", () => {
                        console.log("Click");
                        incrementNum(parseInt(e.innerText));
                    });
                    e.classList.add(c1, c2);
                }
            }

            let el = document.createElement("br");
            document.body.appendChild(el);
        }
    }

    console.log(document.getElementsByClassName("a1"));

    let outText = "";
    outText += "{\n";
    for (let i = 0; i < 6; i++) {
        outText += "{";
        for (let j = 0; j < 8; j++) {
            outText += states[parseInt(c1.substring(1, 2))][parseInt(c2.substring(1, 2))];
            if (j != 7) {
                outText += ", ";
            }
        }
        outText += "}";
        if (i != 5) {
            outText += ",\n";
        }
    }
    outText += "\n}";


    let out = document.createElement("p");
    out.innerText = outText;
    document.body.appendChild(out);
}

function incrementNum(num) {
    return (num + 1) % 6;
}