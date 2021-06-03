# slothengine

This is the second iteration of slothengine. To see the first, check out no-idea-engine (https://github.com/retrogamer500/no-idea-engine).

The differences between the two engines are as follows:

1) slothengine is still under development and it is not quite ready for production use. no-idea-engine is heavily feature-complete.

2) slothengine is renderer/backend agnostic. There is a base implementation using JavaFX included. Since it is backend agnostic, there is no concept of shaders, unless a specific backend uses them. If they are used, that will make it difficult to change backends. no-idea-engine is purely OpenGL 3+, and GLSL shaders are a resource as fundamental as images and sound resources.
