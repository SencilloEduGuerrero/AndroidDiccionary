/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: ENE-JUN/2023    HORA: 08-09 HRS
:*
:*                  Activity que despliega la clase y métodos de Permisos.
:*
:*  Archivo     : PermisoApp.java
:*  Autores     : Eduardo Ivan Guerrero Hernández   19130918
:*              : Janeth Díaz Cruz                  19131498
:*  Fecha       : 20/May/2023
:*  Compilador  : Android Studio Electric Eel 2022.1
:*  Descripción : Este Activity forma parte de la utilidad de permisos, donde se muestra
                  la clase de Permiso con sus métodos.
:*  Ultima modif:
:*  Fecha       Modificó                            Motivo
:*==========================================================================================
:*  20/May/2023  Eduardo Ivan Guerrero Hernández     Se implementó la clase de PermisoApp
                 Janeth Diaz Cruz                    junto con sus métodos.
:*------------------------------------------------------------------------------------------*/

package util;

public class PermisoApp {

    // Declaración de Variables:--------------------------------------------------------------------
    private String permiso      = "";
    private String nombreCorto  = "";
    private boolean obligatorio = false;
    private boolean otorgado    = false;

    // Métodos:-------------------------------------------------------------------------------------
    // Método que recibe los permisos, el nombre y si es obligatorio o no el permiso.
    public PermisoApp ( String permiso, String nombreCorto, boolean obligatorio  ) {
        this.permiso = permiso;
        this.nombreCorto = nombreCorto;
        this.obligatorio = obligatorio;
    }

    // Método que recibe los permisos y los asigna.
    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    // Método que recibe el nombre corto y los asigna.
    public String getNombreCorto() {
        return nombreCorto;
    }

    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }

    // Método que recibe si es obligatorio y los asigna.
    public boolean isObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    // Método que regresa si es obligatorio y lo asigna.
    public boolean isOtorgado() {
        return otorgado;
    }

    public void setOtorgado(boolean otorgado) {
        this.otorgado = otorgado;
    }
}
