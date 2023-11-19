# Final programación 4

## Como usar git

Les dejo una pagina que cree, que te explica como usar git, la voy a esar actualizando.
https://nahuelmosse.github.io/Tutorial-de-Git/

## Como clonar el repo

***Prerrequisitos:*** Tener una cuenta de github creada.

1. Crear la ssh key

	Ejecutar:
	`ssh-keygen -o -t rsa -C "<tu email>"`
	**Importante:** *Remplazar por tu email de github, cuando ponemos "<>" (también conocidos como cocodrilos), significa que hay que remplazarlo por algo, en este caso tu email.*

	*Esta clave te va a permitir conectar tu pc con github
	Ejecutar este comando en la consola cmd de windows, o cualquiera de linux.
	Debería quedar algo de este estilo:
	`ssh-keygen -o -t rsa -C "nahuelMosse@gmail.com"`*

	En todas las opciones, vas a tocar enter, sin ingresar nada.

2. Buscar la ssh key que generamos

	En la consola, se va a mostar en alguno de los renglones un mensaje como:
	`Your public key has been saved in C:\Users\nahue/.ssh/id_rsa.pub.`
	Así es como me Aparicio a mi, pero seguro vos vas a tener otro.

	**Importante:** *puede ser que la carpeta Users, se llame Usuarios, si tu pc esta en español*
	Tenemos que ir a esa carpeta usando el explorador de archivos.
	
	En la carpeta .ssh, deberian haber dos archivos, id_rsa.pub y id_rsa. 
	*En windows, al .pub, lo suele detectar como Microsoft Publisher Document.*
	El que no es .pub, es tu clave privada, y el pub la publica.

3. Copiar la ssh key

	El que nos interesa es id_rsa.pub, la clave publica.
	Lo abrimos con block de notas o algún editor de texto.
	Copiamos todo el contenido.

4. Configurar la ssh key

	En github, tocamos en nuestra imagen, y vamos a settings o configuración.
	Alli buscamos en la subseccion de access, la opcion SSH and GPG keys.
	Ahi adentro, va a haber un boton verde, que dice new SSH key, tocamos ahi.
	
	De title, ponemos un nombre cualquiera, no es importante.
	De key type, dejamos el que esta (Authentication key).
	Y de key, ponemos lo que habíamos copiado anteriormente en el paso 3.
	Finalmente tocamos add SSH key.
	*Posiblemente te pida la contraseña*

*Para este paso, ya tenemos configurada la conexión con github, solo nos queda clonar el repo en nuestra compu*

5. Clonar el repo

	Nos dirigimos al repo que queremos copiar, en este caso, el que estas ahora mismo.
	Tocamos el boton de arriba a la derecha, que dice "<> code"
	Y ahi, cambiamos de HTTPS a SSH, y copiamos el link que nos da.

	Abrimos una git bash en windows o cualquier consola en linux.
	*Generalmente cuando instalan git bash, les va a aparecer una nueva opción en el 		click derecho, "Open Git Bash here", podemos usar esta opción para abrir la consola en la carpeta donde queremos clonar el repo.*
	
	En la teminal, ejecutamos:
	`git clone <link del repo>`
	*Remplazar los cocodrilos por el link del repo de github.*

	Cuando se ejecute, se va a quedar trabado en una pregunta
	`Are you sure you want to continue connecting (yes/no/[fingerprint])?`	Ingresamos yes, y enter.

*Se les debería haber generado una carpeta con el repo donde abrieron la consola, ya solo queda hacer las ediciones correspondientes, y subirlas siguiendo la git de git:*
https://nahuelmosse.github.io/Tutorial-de-Git/
