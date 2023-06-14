/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: ENE-JUN/2023    HORA: 08-09 HRS
:*
:*                    Activity de la clase de la Base de Datos.
:*
:*  Archivo     : DataBase.java
:*  Autores     : Eduardo Ivan Guerrero Hernández   19130918
:*              : Janeth Díaz Cruz                  19131498
:*  Fecha       : 31/May/2023
:*  Compilador  : Android Studio Electric Eel 2022.1
:*  Descripción : Este Activity es la clase de la Base de Datos con todos sus métodos,
                  de asignación y obtención.
:*  Ultima modif:
:*  Fecha       Modificó                            Motivo
:*==========================================================================================
:*  31/May/2023  Eduardo Ivan Guerrero Hernández     Se implementó la clase de Base de Datos
                 Janeth Diaz Cruz                    con todos sus métodos.
:*------------------------------------------------------------------------------------------*/

package com.example.andr_proyecto.SQLite;

// Imports:-----------------------------------------------------------------------------------------
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.ArrayList;

import  com.example.andr_proyecto.Object.Palabra;

// Creación de la clase 'DataBase':-----------------------------------------------------------------
public class DataBase extends SQLiteOpenHelper {

    // Declaración de Variables:--------------------------------------------------------------------
    private static final String NOMBRE_BD = "diccionario.db";
    private static final int VERSION_BD = 2;
    private static final String TABLE_PALABRAS = "PALABRAS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_PALABRA = "PALABRA";
    private static final String COLUMN_SUBTITULO = "SUBTITULO";
    private static final String COLUMN_SIGNIFICADO = "SIGNIFICADO";
    private static final String COLUMN_AUDIO_URL = "AUDIO_URL";

    // Métodos:-------------------------------------------------------------------------------------
    // Método creador de la Base de Datos:----------------------------------------------------------
    public DataBase ( Context context ) {
        super( context, NOMBRE_BD, null, VERSION_BD );
    }

    //----------------------------------------------------------------------------------------------

    /**
     * El método onCreate se encarga de crear la estructura de la base de datos mediante la ejecución
     * de una sentencia SQL. En este caso, parece que la variable TABLE_DICCIONARIO contiene una sentencia
     * SQL para crear una tabla en la base de datos.
     * @param db The database.
     */

    @Override
    public void onCreate( SQLiteDatabase db ) {
        String createTable = "CREATE TABLE " + TABLE_PALABRAS +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_PALABRA + " TEXT, " +
                       COLUMN_SUBTITULO + " TEXT, " +
                       COLUMN_SIGNIFICADO + " TEXT, " +
                       COLUMN_AUDIO_URL + " TEXT)";// URL AUDIO
        db.execSQL( createTable );
    }

    //----------------------------------------------------------------------------------------------

    /**
     * onUpgrade se utiliza en la clase SQLiteOpenHelper para manejar la actualización de la base de
     * datos cuando la versión de la base de datos existente es diferente a la versión esperada por
     * la aplicación.
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_PALABRAS );
        onCreate( db );
    }

    //----------------------------------------------------------------------------------------------

    /**
     * El método clearDataBase() en la clase DB se utiliza para eliminar la tabla de palabras del
     * diccionario y volver a crearla vacía.
     */

    public void clearDataBase() {
        SQLiteDatabase dbWrite = getWritableDatabase();
        dbWrite.execSQL( "DROP TABLE IF EXISTS PALABRAS" );
        onCreate( dbWrite );
    }

    //----------------------------------------------------------------------------------------------

    /**
     *
     * @param palabra
     * @param subtitulo
     * @param significado
     * @param audio_url
     */
    public void agregarPalabra( String palabra, String subtitulo, String significado, String audio_url ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( COLUMN_PALABRA, palabra );
        values.put( COLUMN_SUBTITULO, subtitulo );
        values.put( COLUMN_SIGNIFICADO, significado );
        values.put( COLUMN_AUDIO_URL, audio_url );
        db.insert( TABLE_PALABRAS, null, values );
        db.close();
    }
    //----------------------------------------------------------------------------------------------
    public ArrayList<Palabra> getPalabras() {
        SQLiteDatabase dbRead = getReadableDatabase();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM PALABRAS", null );
        ArrayList<Palabra> palabras = new ArrayList<>();
        if ( cursor.moveToFirst() ) {
            do {
                int id = cursor.getInt(0);
                String palabra = cursor.getString(1 );
                String subtitulo = cursor.getString(2 );
                String significado = cursor.getString(3 );
                String audio_url = cursor.getString(4 );
                //byte[] audio = cursor.getBlob(3);
                palabras.add( new Palabra( id, palabra, subtitulo, significado, audio_url ) );
            } while ( cursor.moveToNext() );
        }
        return palabras;
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Metodo para eliminar palabra de la base de datos del diccionario, tomamos como parametro el id
     * de la palabra para poder eliminarla.
     * @param id
     */

    public void eliminarPalabra( int id ) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PALABRAS, COLUMN_ID + "=?", new String[]{ String.valueOf( id ) } );
        db.close();
    }
    //----------------------------------------------------------------------------------------------

    /**
     * Metodo para modificar las palabras de nuestro diccionario
     * @param id
     * @param palabra
     */

    public void modificarPalabra( int id, Palabra palabra ) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( COLUMN_PALABRA, palabra.getPalabra());
        values.put( COLUMN_SUBTITULO, palabra.getSubtitulo());
        values.put( COLUMN_SIGNIFICADO, palabra.getSignificado());
        values.put( COLUMN_AUDIO_URL,palabra.getAudio_url() );
        db.update( TABLE_PALABRAS, values, COLUMN_ID + "=?", new String[]{ String.valueOf( id ) } );
        db.close();
    }
}