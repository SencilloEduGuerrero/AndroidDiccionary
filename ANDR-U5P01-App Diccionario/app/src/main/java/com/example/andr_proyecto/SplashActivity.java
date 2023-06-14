/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: ENE-JUN/2023    HORA: 08-09 HRS
:*
:*                  Activity que despliega una pantalla de carga.
:*
:*  Archivo     : SplashActivity.java
:*  Autores     : Eduardo Ivan Guerrero Hernández   19130918
:*              : Janeth Díaz Cruz                  19131498
:*  Fecha       : 22/Abr/2023
:*  Compilador  : Android Studio Electric Eel 2022.1
:*  Descripción : Este Activity despliega la pantalla de carga con duración de 2 segundos.
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*  
:*------------------------------------------------------------------------------------------*/

package com.example.andr_proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    //_______________________________________________________________________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Hacer la transición a ListasActivity después de 2 segundos.
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent( SplashActivity.this, ListasActivity.class );
                startActivity( intent );
                finish();
            }
        }, 2000);
    }



    // __________________________________________________________________________________________________________________________
    // __________________________________________________________________________________________________________________________

    @Override
    public void onBackPressed() {
        // Se anula el salir con el botón de atrás para evitar bugs.
    }

    //_______________________________________________________________________________________________________________________________
}