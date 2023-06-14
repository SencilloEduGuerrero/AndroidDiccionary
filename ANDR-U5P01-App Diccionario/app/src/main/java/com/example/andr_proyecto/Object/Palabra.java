/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: ENE-JUN/2023    HORA: 08-09 HRS
:*
:*            Activity que crea y asigna los métodos de la Clase Palabra.
:*
:*  Archivo     : Palabra.java
:*  Autores     : Eduardo Ivan Guerrero Hernández   19130918
:*              : Janeth Díaz Cruz                  19131498
:*  Fecha       : 31/May/2023
:*  Compilador  : Android Studio Electric Eel 2022.1
:*  Descripción : Este Activity muestra la clase Palabra junto con todos sus métodos.
:*  Ultima modif:
:*  Fecha       Modificó                            Motivo
:*==========================================================================================
:*  31/May/2023  Eduardo Ivan Guerrero Hernández     Se creó la clase Palabra donde se almacenan
                 Janeth Diaz Cruz                    su constructor, métodos y asignaciones.
:*------------------------------------------------------------------------------------------*/

package com.example.andr_proyecto.Object;

// Creación de la clase Palabra:--------------------------------------------------------------------
public class Palabra {

    // Declaración de Variables:--------------------------------------------------------------------
    private int id;
    private String palabra, subtitulo, significado, audio_url;

    // Métodos:-------------------------------------------------------------------------------------
    // Método público de Palabra:-------------------------------------------------------------------
    public Palabra() { }

    // Constructor de la clase Palabra para la Base de Datos:---------------------------------------
    public Palabra( int id, String palabra, String subtitulo, String significado, String audio_url )
    {
        this.id = id;
        this.palabra = palabra;
        this.subtitulo = subtitulo;
        this.significado = significado;
        this.audio_url = audio_url;
    }

    // Constructor de la clase Palabra:-------------------------------------------------------------
    public Palabra( String palabra, String subtitulo, String significado )
    {
        this.palabra = palabra;
        this.subtitulo = subtitulo;
        this.significado = significado;
    }

    // Asignación y Obtención de ID:----------------------------------------------------------------
    public int getId() {
        return id;
    }

    //----------------------------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    // Asignación y Obtención de Palabra:-----------------------------------------------------------
    public String getPalabra() {
        return palabra;
    }

    //----------------------------------------------------------------------------------------------

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    // Asignación y Obtención de Subtitulo:---------------------------------------------------------
    public String getSubtitulo() {
        return subtitulo;
    }

    //----------------------------------------------------------------------------------------------

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    // Asignación y Obtención de Significado:-------------------------------------------------------
    public String getSignificado() {
        return significado;
    }

    //----------------------------------------------------------------------------------------------

    public void setSignificado(String significado) {
        this.significado = significado;
    }

    // Asignación y Obtención de Audio_URL:---------------------------------------------------------
    public String getAudio_url() {
        return audio_url;
    }

    //----------------------------------------------------------------------------------------------

    public void setAudio_url(String audio_url) {
        this.audio_url = audio_url;
    }

    //----------------------------------------------------------------------------------------------
}