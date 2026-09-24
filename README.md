# Event Logistic

## Integrantes

- Ballesteros Sanchez Juan Esteban
- Mercado Álvarez Olger Rafael
- Ortiz Diaz Juan David
- Urrego Graciano Sebastian

## Descripción

El proyecto que se desarrolló se trata sobre un sistema que se encarga de administrar eventos que deben contener personal logístico. Este sistema se encarga de administrar el personal necesario para cada evento. Además, permite gestionar los eventos en los cuales se debe contar con el personal necesario para atender dicho evento.

## Funciones principales

Actualmente, el sistema no contiene todas las funcionalidades requeridas; sin embargo, ya contiene algunas funcionalidades principales. Entre ellas se tiene el ingreso de cada tipo de usuario (Logístico, Coordinador y Manager) al sistema, en el cual cada uno solo puede ejecutar las acciones a las que tiene acceso, teniendo en cuenta su rol.

Además, se puede visualizar el perfil de cada persona, realizar la administración del personal logístico y también la administración de eventos.

Para tener en cuenta, el sistema todavía necesita crear una serie de funciones principales, entre las cuales se encuentra la administración de coordinadores y la asignación tanto de logísticos como de coordinadores a los eventos. También se debe agregar la asignación máxima de personal en cada evento.

También hace falta la configuración completa de un superusuario, el cual permita crear los managers y ejecutar todas las funcionalidades restantes de la aplicación sin importar el rol.

## Estructura del proyecto

La arquitectura utilizada en este proyecto es una arquitectura por capas, donde cada capa se encarga de una funcionalidad diferente. Las capas son las siguientes:

**Capa de modelos o de dominio:** En esta capa es donde almacenamos todos los modelos de la aplicación. Estos modelos representan, de alguna manera, la lógica de negocio del proyecto.

**Capa de repositorios:** Esta capa se encarga del acceso a los datos de la aplicación. Por el momento, no hay una persistencia de datos, pero luego se incluirá esta funcionalidad haciendo uso de una base de datos. El resultado del acceso a los datos, ya sea de consulta o de escritura, está dado por los modelos que están en la capa de modelos o de dominio.

**Capa de servicios o lógica:** Esta capa se encarga de realizar específicamente las funcionalidades del sistema, como, por ejemplo, crear un logístico, actualizar un logístico, entre otras. Además, esta capa debe contener toda la lógica de negocio necesaria para realizar estas acciones, como validaciones, conversiones y también servir como una puerta para el acceso a los datos.

**Capa de UI:** Aquí es donde se encuentra todo el apartado visual de la aplicación, además de conectarse con la capa de servicios para llamar la lógica necesaria para ejecutar las funcionalidades del sistema por medio de la interfaz gráfica que se encuentra en esta capa.

**Capa de interfaces:** Esta capa contendrá todas las interfaces del sistema. Sin embargo, actualmente solo contiene una interfaz. Esto se debe a que actualmente el proyecto no aplica inversión de dependencias. Sin embargo, esto se agregará para las próximas entregas.

## Diseño orientado a objetos

El proyecto está aplicando los cuatro pilares de la programación orientada a objetos, los cuales estamos aplicando de la siguiente manera:

**Herencia:** En el apartado de la herencia, la estamos aplicando a través de una clase padre llamada "Personal", de la cual heredan tres clases hijas: Logístico, Coordinador y Manager. Cada uno de estos contiene comportamientos similares; sin embargo, cada uno tiene funcionalidades diferentes que les corresponden.

**Abstracción:** La abstracción se utiliza a través de la clase padre "Personal", ya que tanto Logístico, Coordinador y Manager comparten atributos similares. Entonces, se abstrajeron estos atributos en la clase "Personal" y, de esta manera, tanto Logístico, Coordinador y Manager utilizan estos atributos abstraídos.

**Encapsulamiento:** El encapsulamiento se usa en cada uno de los modelos para no acceder directamente a ellos y preservar la integridad de los modelos. También se utiliza para funciones que solo le competen a la sección específica en la cual se encuentran y que no deberían ser llamadas desde otros lugares.

**Polimorfismo:** El polimorfismo lo estamos aplicando en una sola funcionalidad, que es la que se encarga de calcular el salario de cada tipo de usuario, ya sea Logístico, Coordinador o Manager, ya que cada uno tiene una forma diferente de calcular su salario. Sin embargo, debemos buscar nuevas formas de aplicar más polimorfismo dentro de la aplicación.

## Ejecución del proyecto

Para ejecutar el proyecto, se debe clonar el repositorio del proyecto específicamente en la rama `master` y no en las demás ramas, ya que estas son para el desarrollo independiente de cada uno de los integrantes. Además, `master` corresponde a la rama principal del proyecto.

Luego, simplemente se deberá ejecutar el proyecto para probar cada una de las funcionalidades, ya que por el momento no se ha terminado un usuario superusuario que le permita ejecutar todas las acciones del sistema.

Por esta razón, se debe ingresar con cada diferente tipo de usuario: Logístico, Coordinador y Manager. Los usuarios correspondientes a estos son:

### Logístico

**Usuario o correo:** [sebastian@gmail.com](mailto:sebastian@gmail.com)

**Contraseña:** sebas123

### Coordinador

**Usuario o correo:** [robert@gmail.com](mailto:robert@gmail.com)

**Contraseña:** robert123

### Manager

**Usuario o correo:** admin

**Contraseña:** admin123

### Repositorio (Github)

[https://github.com/19206406/EventLogistics](mailto:https://github.com/19206406/EventLogistics)
