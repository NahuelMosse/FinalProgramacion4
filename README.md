# Final programacion 4

## Como usar git

    Les dejo una pagina que cree, que te explica como usar git, la voy a esar actualizando.
    https://nahuelmosse.github.io/Tutorial-de-Git/

## Como clonar el repo

    1-  Crear la ssh key
        Esta clave te va a permitir conectar tu pc con github.

        Para crearla, ejecutar este comando en la consola cmd de windows, o cualquiera de linux.
        **Importante:** *Remplazar por tu email, cuando ponemos "<>", significa que hay que remplazarlo.*

        `ssh-keygen -o -t rsa -C "<tu email>"`

        Deveria quedar algo de este estilo:

        `ssh-keygen -o -t rsa -C "nahuelMosse@gmail.com"`

    2-  Buscar la ssh key que generamos

        En la consola, se va a mostar en alguno de los renglones un mensaje como:

        `Your public key has been saved in C:\Users\nahue/.ssh/id_rsa.pub.`

        Asi se vio en mi compu cuando lo genere, en tu caso se puede ver distinto porque tu usuario debe ser distinto.

        Tenemos que ir a esa carpeta usando el explorador de archivos.
        **Importante:** *puede ser que la carpeta Users, se llame Usuarios, si tu pc esta en español*

        En la carpeta .ssh, deberian haber dos archivos, id_rsa.pub y id_rsa. En windows, al .pub, lo suele detectar como Microsoft Publisher Document.

        El que no es .pub, es tu clave privada, y el pub la publica.

    3-  
    
    

