#include interstellar:sphere

uniform vec2 ScreenSize;

out vec4 fragColor;

void main() {
//    fragColor = vec4(1, 0, 1, 1);

    vec3 dir = rayDirection(45.0, ScreenSize.xy, gl_FragCoord);
    vec3 eye = vec3(0.0, 0.0, 5.0);
    float dist = shortestDistanceToSurface(eye, dir, MIN_DIST, MAX_DIST);

    if (dist > MAX_DIST - EPSILON) {
        // Didn't hit anything
        fragColor = vec4(0.0, 0.0, 0.0, 0.0);
        return;
    }

    fragColor = vec4(1.0, 0.0, 0.0, 1.0);
}
