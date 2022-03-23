# framework

Current version: 0.0.1

Discord: [Discord](https://discord.gg/teSGwVf64R)

Simple framework for developing simple plugins for minecraft servers

This framework doesn't contain any cOoLfEaTuReS, just few utilities that may reduce developing time

Open issue if you have any suggestions or smth like that

Mainly, framework created with intent on dependency injection, and require `guice` for work

### Using:

- Gradle
  ```groovy
    repositories {
        mavenCentral()
    }
  
    dependencies {
        compileOnly 'io.github.maxwainer:framework-commons:<Major>.<Minor>.<Revision>'
            
        // highly recommend include them in jar if you really using this 
        implementation 'io.github.maxwainer:framework-loader:<Major>.<Minor>.<Revision>'
        implementation 'io.github.maxwainer:framework-bootstrap:<Major>.<Minor>.<Revision>'
        // bootstrap required also to implementation
        implementation 'io.github.maxwainer:framework-bukkit-bootstrap-implementation:<Major>.<Minor>.<Revision>'
  
        // implementation of some modules
        compileOnly 'io.github.maxwainer:framework-<platform>-<module>-implementation:<Major>.<Minor>.<Revision>'
        
        compileOnly 'io.github.maxwainer:framework-commands:<Major>.<Minor>.<Revision>'
  
        compileOnly 'io.github.maxwainer:framework-config-api:<Major>.<Minor>.<Revision>'
        compileOnly 'io.github.maxwainer:framework-config-bukkit-adapters:<Major>.<Minor>.<Revision>'
        compileOnly 'io.github.maxwainer:framework-config-yaml:<Major>.<Minor>.<Revision>'
  
        compileOnly 'io.github.maxwainer:framework-locale:<Major>.<Minor>.<Revision>'
        compileOnly 'io.github.maxwainer:framework-orm-api:<Major>.<Minor>.<Revision>'
        compileOnly 'io.github.maxwainer:framework-packet-wrapper:<Major>.<Minor>.<Revision>'
        compileOnly 'io.github.maxwainer:framework-stack-wrapper:<Major>.<Minor>.<Revision>'
        compileOnly 'io.github.maxwainer:framework-bukkit-commons:<Major>.<Minor>.<Revision>'
    }
  ```