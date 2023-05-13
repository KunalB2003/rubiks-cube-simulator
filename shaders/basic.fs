#version 330

uniform vec4 color;
out vec4 fragColor;

void main() {
    // fragColor = vec4(0, 1, 0.67, 1);
    fragColor = color;
}