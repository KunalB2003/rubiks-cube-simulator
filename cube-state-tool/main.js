window.onload = () => {

    var states = [];

    for (let i = 0; i < 6; i++) {
        states.push([]);
        for (let j = 0; j < 8; j++) {
            states[i][j] = i;
        }
    }

    for (let i = 0; i < 3; i++) {
        for (let i2 = 0; i2 < 3; i2++) {

            for (let j = 0; j < 4; j++) {
                for (let j2 = 0; j2 < 3; j2++) {
                    let e = document.createElement("button");
                    document.body.appendChild(e);

                    let text = "!";
                    let c1 = -1;
                    let c2 = -2;

                    if ((i == 0 || i == 2) && j != 1) {
                        e.style.cssText = "visibility:hidden";
                    } else {
                        if (i == 0 && j == 1) {
                            text = 2;
                            c1 = 2;
                        } else if (i == 1 && j == 0) {
                            text = 1;
                            c1 = 1;
                        } else if (i == 1 && j == 1) {
                            text = 0;
                            c1 = 0;
                        } else if (i == 1 && j == 2) {
                            text = 4;
                            c1 = 4;
                        } else if (i == 1 && j == 3) {
                            text = 5;
                            c1 = 5;
                        } else if (i == 2 && j == 1) {
                            text = 3;
                            c1 = 3;
                        }
                        switch (true) {
                            case (i2 == 0 && j2 == 0):
                                c2 = 0;
                                break;
                            case (i2 == 0 && j2 == 1):
                                c2 = 1;
                                break;
                            case (i2 == 0 && j2 == 2):
                                c2 = 2;
                                break;
                            case (i2 == 1 && j2 == 0):
                                c2 = 7;
                                break;
                            case (i2 == 1 && j2 == 1):
                                c2 = 8;
                                break;
                            case (i2 == 1 && j2 == 2):
                                c2 = 3;
                                break;
                            case (i2 == 2 && j2 == 0):
                                c2 = 6;
                                break;
                            case (i2 == 2 && j2 == 1):
                                c2 = 5;
                                break;
                            case (i2 == 2 && j2 == 2):
                                c2 = 4;
                                break;
                        }
                    }
                    e.innerHTML = text;
                    e.classList.add(c1, c2);
                }


                
            }

            let el = document.createElement("br");
            document.body.appendChild(el);
        }
    }
}