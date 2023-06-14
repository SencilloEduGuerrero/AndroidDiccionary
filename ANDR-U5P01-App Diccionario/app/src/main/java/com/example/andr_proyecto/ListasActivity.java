import android.widget.ListView;
import android.widget.Toast;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

    // Declaración de Variables:--------------------------------------------------------------------
    private ArrayList<Integer> posicionSeleccionada = new ArrayList<>();
    private ListView ListaDic;
    private SearchView buscarLista;
    private String rutaArchivo = rutaExt.getAbsolutePath() + "/DCIM";

        ListaDic.setAdapter( adaptador );

        buscarLista.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

        // Damos la forma del arreglo.
        ListaDic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick( AdapterView<?> adapterView, View view, int position, long id ) {
                String itemText = ( String ) ListaDic.getItemAtPosition( position );

                // Buscar una palabra en la base de datos por descripción.
                palabra = findByDesc( itemText );

                // Muestra un mensaje para mostrar los datos del nombre, definición y audio, no tiene otra función.
                AlertDialog.Builder builder = new AlertDialog.Builder(ListasActivity.this );
                builder.setTitle( palabra.getPalabra() )
                       .setMessage( palabra.getSignificado() )
                       .setIcon( R.drawable.itl )
                       .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialogInterface, int i ) {
                                Toast.makeText(ListasActivity.this, palabra.getPalabra(),
                                        Toast.LENGTH_SHORT ).show();
                            }
                        } )
                        .setNeutralButton("Reproducir", new DialogInterface.OnClickListener() {
                            // Botón que reproduce audios, por default los audios no guardados
                            // son null.
                            @Override
                            public void onClick( DialogInterface dialogInterface, int i ) {
                                    try {
                                                Toast.makeText(ListasActivity.this,
                                                        "Reproduciendo Audio de " +
                                                                palabra.getPalabra(),
                                                        Toast.LENGTH_SHORT ).show();
                                            }
                                        } );
                                            }
                                        } );
                                    } catch ( IOException ex ) {
                                        Toast.makeText(ListasActivity.this,
                                                "Audio no encontrado.",
                                                Toast.LENGTH_SHORT ).show();
                                    }
                                } else {
                                    Toast.makeText(ListasActivity.this,
                                            "No hay audio existente.",
                                            Toast.LENGTH_SHORT ).show();
                                }
                            }
                        } )
                        .create()
                        .show();
            }
        });
        setOnLongClickEvent();
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    private Palabra findByDesc( String desc ) {
        // Paso 1: creamos instancia a base de datos
        DataBase db = new DataBase(getApplicationContext());

        // Paso 2: creamos arreglo para almacenar palabras
        ArrayList<Palabra> palabrasDB = new ArrayList<>();

        palabrasDB = db.getPalabras();

        // Función lamda para obtener la palabra de la base de datos por descripción
        return palabrasDB.stream().filter( ( x ) ->
                x.getPalabra().equals( desc ) ).findFirst().get();
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    // Método para Checar los Permisos:-------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults )
    {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );

        // Verifica si los permisos estan activos y pide los permisos.
        if ( requestCode == ChecadorDePermisos.CODIGO_PEDIR_PERMISOS ) {
            ChecadorDePermisos.verificarPermisosSolicitados ( this, permisosReq, permissions, grantResults );
        }
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    // Botón al mantener presionado el botón cambiará de color.
    private void setOnLongClickEvent() {
        ListaDic.setOnItemLongClickListener((parent, view, position, id) -> {
            int selectedPosition = posicionSeleccionada.size() > 0 ? posicionSeleccionada.get(0) : -1;

            // Paso 1: Seleccionar o deseleccionar el item
            if (selectedPosition == position) {
                // Si se selecciona otro, y ya habia uno, se deselecciona el anterior.
                posicionSeleccionada.clear();
                view.setBackgroundColor(Color.WHITE);
            } else {
                // Selecciona el nuevo item, y asigna la posición a este.
                if (selectedPosition != -1) {
                    View previousView = parent.getChildAt(selectedPosition);
                    if (previousView != null) {
                        previousView.setBackgroundColor(Color.WHITE);
                    }
                }
                posicionSeleccionada.clear();
                posicionSeleccionada.add(position);
                view.setBackgroundColor(Color.parseColor("#172780"));
            }

            // Paso 2: validamos si es el unico elemento en la lista ocultamos el boton de crear
            // y mostramos el editar y borrar
            if( posicionSeleccionada.size() == 1 ) {
                btnCrear.setVisibility ( View.INVISIBLE );
                btnEditar.setVisibility( View.VISIBLE   );
                btnBorrar.setVisibility( View.VISIBLE   );
            }
            // Paso 3 validamos si ya no hay ningun elemento en el arreglo y mostramos el boton
            // crear y ocultamos el boton borrar y editar
            else if( posicionSeleccionada.size() == 0 ) {
                btnCrear.setVisibility ( View.VISIBLE   );
                btnEditar.setVisibility( View.INVISIBLE );
                btnBorrar.setVisibility( View.INVISIBLE );
            }
            return true;
        } );
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    // validamos si existe el item seleccionado en el arreglo
    private boolean validarPosicion( int position ) {
        for ( int item: posicionSeleccionada ) {
            if (item == position) {
                return true;
            }
        }
        return false;
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    // Botón de Crear Nuevo o Añadir:---------------------------------------------------------------
    public void btnNuevoClick ( View V ) {
        // Creamos un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this );

        // Le asignamos el titulo, nombre y creamos un layout para darle base a nuestro EditText.
        builder.setTitle( "Crear" );
        builder.setIcon( R.drawable.itl );

        // Evitamos que el Layout pueda girar.
        LinearLayout layout = new LinearLayout(ListasActivity.this );
        layout.setOrientation( LinearLayout.VERTICAL );

        layout.addView( inputN );
        layout.addView( inputD );

        layout.addView( btnAudio   );
        layout.addView( btnDetener );

        btnAudio.setText( "Grabar" );
        btnAudio.setBackgroundColor( Color.parseColor("#172780" ) );

        btnDetener.setText( "Detener" );
        btnDetener.setBackgroundColor( Color.parseColor("#A52A21" ) );
        btnDetener.setVisibility( View.INVISIBLE );

        // Le asignamos un margen para que el EditText tenga una buena forma.
        layout.setPadding(10,5,10,5 );
        builder.setView( layout );

        // Evento de presionar el botón, comenzará a grabar el audio.
        btnAudio.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                inputN.setEnabled(false);
                // Toma el nombre del input y verifica si no está vacío el texto.
                nombreAudio = inputN.getText().toString();

                    mediaRecorder = new MediaRecorder();

                    // Establecer el MICROFONO como fuente de audio.
                    mediaRecorder.setAudioSource( MediaRecorder.AudioSource.MIC );

                    // Establecemos el formato de archivo en 3GP.
                    mediaRecorder.setOutputFormat( MediaRecorder.OutputFormat.THREE_GPP );

                    // Establece el codificador de audio.
                    mediaRecorder.setAudioEncoder( MediaRecorder.AudioEncoder.AMR_WB );

                    // Establecer el archivo de salida de la grabación.
                    mediaRecorder.setOutputFile( fichero );

                    try {
                        // Una vez presionado el boton de grabar, se quita y se muestra el
                        // botón de detener.
                        btnAudio.setVisibility  ( View.INVISIBLE );
                        btnDetener.setVisibility( View.VISIBLE   );

                        mediaRecorder.prepare();
                        mediaRecorder.start();

                        Toast.makeText(ListasActivity.this,
                                "Grabando Audio.", Toast.LENGTH_SHORT )
                                .show();

                        btnDetener.setOnClickListener( new View.OnClickListener() {
                            @Override
                            public void onClick( View v ) {
                                // Desactiva la validación de audio y activa
                                // el botón de grabar.
                                grabando = false;

                                btnDetener.setVisibility( View.INVISIBLE );
                                btnAudio.setVisibility  ( View.VISIBLE   );

                                mediaRecorder.stop();
                                mediaRecorder.release();

                                Toast.makeText(ListasActivity.this, "Audio Grabado "
                                        + nombreAudio, Toast.LENGTH_SHORT ).show();
                            }
                        } );
                    } catch ( IOException Ex ) {
                        Toast.makeText(ListasActivity.this,
                                "Fallo al hacer la grabación.", Toast.LENGTH_SHORT )
                                .show();
                    }
                } else {
                    Toast.makeText(ListasActivity.this,
                            "Favor de asignar un Nombre.", Toast.LENGTH_SHORT )
                            .show();
                }
            }
        } );

        // Cuando se cancela la ventana, no hace nada.
        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                // Verifica si el botón de grabar esta activo, detene la grabación
                // mas no lo guarda.
                if ( grabando ) {
                    mediaRecorder.stop();
                    grabando = false;
                }
            }
        } );

        // Al aceptar tomamos los valores de los EditText, verificando si los valores no estan
        // vacíos, para que salte al siguiente proceso donde guarda los datos en un nuevo arreglo
        // para mostrarlos.
        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog, int which ) {
                 DataBase dataBase = new DataBase(getApplicationContext());
                 ArrayList<Palabra> palabras = dataBase.getPalabras();

                DataBase db = new DataBase( getApplicationContext() );
                palabras = db.getPalabras();

                auxN = inputN.getText().toString();
                auxD = inputD.getText().toString();

                if ( grabando ) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    grabando = false;

                    Toast.makeText(ListasActivity.this, "Audio Grabado "
                            + nombreAudio, Toast.LENGTH_SHORT ).show();
                }

                if ( auxN.isEmpty() ) {
                    Toast.makeText(ListasActivity.this,
                            "Favor de Asignar un Nombre.", Toast.LENGTH_SHORT )
                            .show();
                } else {
                    if ( auxD.isEmpty() && fichero.equals( "" ) ) {
                        Toast.makeText(ListasActivity.this,
                                "Favor de Asignar una Descripción o Audio.",
                                Toast.LENGTH_SHORT ).show();
                    } else {
                        // Verifica si ya existe una palabra ya declarada.
                        if ( palabras.stream().filter( ( x ) ->
                                x.getPalabra().equals( auxN ) ).findAny().isPresent() ) {
                            Toast.makeText(ListasActivity.this,
                                    "El nombre ya existe.",
                                    Toast.LENGTH_SHORT ).show();
                        } else {
                            // Si no hay ningún problema inserta la nueva definición con todo su contenido.
                            Palabra palabraInsert = new Palabra( auxN, "", auxD );
                            palabraInsert.setAudio_url(fichero);

                            db.agregarPalabra( palabraInsert.getPalabra(),
                                               palabraInsert.getSubtitulo(),
                                               palabraInsert.getSignificado(),
                                               palabraInsert.getAudio_url() );
                            palabras = db.getPalabras();

                            ListaDic = findViewById( R.id.ListaDic );

                            ArrayList<String>  palabrasDesc = new ArrayList<>();
                            palabras.forEach((x) -> palabrasDesc.add( x.getPalabra() ) );

                            ListaDic.setAdapter( adaptador );

                            Toast.makeText(ListasActivity.this,
                                    "Se ha añadido una nueva palabra: " + auxN,
                                    Toast.LENGTH_SHORT ).show();
                        }
                    }
                }
            }
        } );
        builder.show();
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    // Botón de Modificar:--------------------------------------------------------------------------
    public void btnModificarClick(View v) {
        // Creamos el AlertDialog para mostrar mensajes.
        AlertDialog.Builder builder = new AlertDialog.Builder(ListasActivity.this);

        // Le damos forma al cuadro de texto.
        builder.setTitle("Modificar");
        builder.setIcon(R.drawable.itl);

        // Creamos el Layout para poder tener un EditText para poder modificar.
        LinearLayout layout = new LinearLayout(ListasActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        // Los EditText se pueden editar y muestran el texto del objeto.
        builder.setMessage("Modificación del Nombre y Definición:");

        layout.addView(inputN);
        layout.addView(inputD);
        layout.addView(btnAudio);
        layout.addView(btnDetener);

        btnAudio.setText("Grabar");
        btnAudio.setBackgroundColor(Color.parseColor("#172780"));

        btnDetener.setText("Detener");
        btnDetener.setBackgroundColor(Color.parseColor("#A52A21"));
        btnDetener.setVisibility(View.INVISIBLE);

        String itemText = (String) ListaDic.getItemAtPosition(posicionSeleccionada.get(0));
        palabra = findByDesc(itemText);

        inputN.setText(palabra.getPalabra());
        inputD.setText(palabra.getSignificado());

        layout.setPadding(10, 5, 10, 5);
        builder.setView(layout);

        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputN.setEnabled(false);
                nombreAudio = inputN.getText().toString();

                if (!nombreAudio.equals("")) {
                    // Se pone como verdadero un booleano como verdadero para validar.
                    grabando = true;

                    fichero = ruta + nombreAudio + ".3gp";
                    mediaRecorder = new MediaRecorder();

                    // Establecer el MICROFONO como fuente de audio.
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

                    // Establecemos el formato de archivo en 3GP.
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

                    // Establece el codificador de audio.
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);

                    // Establecer el archivo de salida de la grabación.
                    mediaRecorder.setOutputFile(fichero);

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();

                        Toast.makeText(ListasActivity.this, "Grabando Audio.", Toast.LENGTH_SHORT).show();

                        btnDetener.setVisibility(View.VISIBLE);
                        btnAudio.setVisibility(View.INVISIBLE);

                        btnDetener.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mediaRecorder.stop();
                                mediaRecorder.release();
                                grabando = false;

                                Toast.makeText(ListasActivity.this, "Audio Grabado " + nombreAudio, Toast.LENGTH_SHORT).show();

                                btnDetener.setVisibility(View.INVISIBLE);
                                btnAudio.setVisibility(View.VISIBLE);
                            }
                        });
                    } catch (IOException ex) {
                        Toast.makeText(ListasActivity.this, "Fallo al hacer la grabación.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // El botón de Modificar crea valores que guardan el valor de los EdiText
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auxN = inputN.getText().toString();
                auxD = inputD.getText().toString();

                if (grabando) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    grabando = false;

                    Toast.makeText(ListasActivity.this, "Audio Grabado " + nombreAudio, Toast.LENGTH_SHORT).show();
                }

                if (auxN.isEmpty()) {
                    Toast.makeText(ListasActivity.this, "Favor de asignar un Nombre.", Toast.LENGTH_SHORT).show();
                } else {
                    if (auxD.isEmpty() && fichero.equals("")) {
                        Toast.makeText(ListasActivity.this, "Favor de asignar una Descripción o Audio.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Al pasar todas las validaciones, obtiene los nuevos valores para
                        // Asignarlos a la palabra original y modificarla.
                        ListaDic = findViewById(R.id.ListaDic);

                        ListaDic.setAdapter(adaptador);

                        // Método Alfabético.
                        ordenarAlfabeto();

                        Toast.makeText(ListasActivity.this, "Se ha Modificado: " + auxN, Toast.LENGTH_SHORT).show();

                        btnCrear.setVisibility(View.VISIBLE);
                        btnEditar.setVisibility(View.INVISIBLE);
                        btnBorrar.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
            // No hace nada.
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (grabando) {
                    mediaRecorder.stop();
                }
            }
        });
        builder.show();
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    // Botón de Eliminar:---------------------------------------------------------------------------
    public void btnEliminarClick ( View V ) {
        String itemText = ( String ) ListaDic.getItemAtPosition( posicionSeleccionada.get(0) );
        palabra = findByDesc( itemText );

        auxN = palabra.getPalabra();

        // Muestra un mensaje en la pantalla.
        AlertDialog.Builder builder = new AlertDialog.Builder(ListasActivity.this );
        builder.setTitle( "Eliminar" )
                .setMessage( "¿Quiere eliminar el Nombre, Definición y Audio?\n\n\t"
                             + palabra.getPalabra() + "\n"
                             + palabra.getSignificado() + "\n"
                             + palabra.getAudio_url() + "\n")
                .setIcon( R.drawable.itl )
                .setCancelable( false )
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                // Si se presiona el botón eliminar, elimina los valores de la posición guardada y luego actualiza.
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fichero.equals("");

                        ListaDic = findViewById( R.id.ListaDic );

                        ListaDic.setAdapter( adaptador );

                        Toast.makeText(ListasActivity.this, "Se ha Eliminado: "
                                + auxN, Toast.LENGTH_SHORT ).show();

                        auxN = "";
                        dialogInterface.dismiss(); // Cerramos el Alert.

                        btnCrear.setVisibility ( View.VISIBLE   );
                        btnEditar.setVisibility( View.INVISIBLE );
                        btnBorrar.setVisibility( View.INVISIBLE );
                    }
                } )
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                // No hace nada, solo vacía la lista de mostrar.
                    @Override
                    public void onClick( DialogInterface dialog, int which ) { }
                } )
                .create()
                .show();
    }

    /* _____________________________________________________________________________________________
    ______________________________________________________________________________________________*/

    // Método de Ordenamiento Alfabético.
    public void ordenarAlfabeto() {
            ListaDic = findViewById(R.id.ListaDic);

            DataBase db = new DataBase(getApplicationContext());
            ArrayList<Palabra> palabras = db.getPalabras();
            ArrayList<String> palabrasDesc = new ArrayList<>();

            // Obtiene las palabras y las agrega.
            for (Palabra palabra : palabras) {
                if (palabra.getPalabra() != null) {
                    palabrasDesc.add(palabra.getPalabra());
                }
            }

            // Reacomoda las nuevas palabras para compararlas alfabéticamente.
            Collections.sort(palabrasDesc, new Comparator<String>() {
            ListaDic.setAdapter(adaptador);
        }

    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item )
    {
        switch ( item.getItemId() ) {
            case R.id.item1: {
                // El primer item, es de información para mostrar el acerca de.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Acerca de...")
                        .setMessage("U4 ProyectoApp v3.0\n" +
                                "por: Eduardo Ivan Guerrero Hernández #19130918\n" +
                                "Janeth Diaz Cruz #19131498\n" +
                                "Enero - Junio 2023")
                        .setIcon(R.drawable.itl)
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mostrarToast("");
                                dialogInterface.dismiss(); // Cerramos el Alert.
                            }
                        })
                        .create()
                        .show();

                return true;
            }

        case R.id.item2: {
            // El segundo item es para Importar o abrir 'Diccionario.txt' para leerlo.
                if (isAlmExtLeible()) {
                    // Asignamos la ruta, y el nombre del archivo que necesitaremos.
                    File archivo = new File(ruta, nombreText);
                    DataBase db = new DataBase(getApplicationContext());

                    // Se limpia la base de datos para agregar los nuevos valores.
                    db.clearDataBase();

                    ArrayList<String> palabrasDesc = new ArrayList<>();
                    String name = "", subtitulo = "", description = "", audiostring = "";

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(archivo));
                        String linea;

                        while ((linea = br.readLine()) != null) {
                            String[] values = linea.split("\\|", -1);
                            // Los separa si hay un '|' y el limite de -1 es para tomar valores vacios.

                            if (values.length >= 3) {
                                name = values[0];

                                // Si los subtitulos estan vacios, pero hay descripcion, lo asigna
                                // por default.
                                // Si los subtitulos no están vacios, ni la descripción, agrega los
                                // subtitulos a la descripción.
                                // Si los subtitulos no estan vacios pero la descripción si, añade
                                // los subtitulos a la descripción.
                                if( values[1].equals("") && !values[2].equals("") ) {
                                    subtitulo = values[1];
                                    description = values[2];
                                } else if ( !values[1].equals("") && !values[2].equals("") ) {
                                    subtitulo = values[1];
                                    description = values[1] + ": " + values[2];
                                } else if ( !values[1].equals("") && values[2].equals("") ) {
                                    subtitulo = values[1];
                                    description = values[1];
                                }

                                if (description == null) {
                                    description = "";
                                }

                                // Si hay audio, el arreglo pasa a ser de 4.
                                audiostring = (values.length >= 4) ? values[3] : "";

                                // Carga los valores y los agrega para hacer el arreglo correctamente.
                                Palabra palabraInsert = new Palabra(name, subtitulo, description);
                                palabraInsert.setAudio_url(audiostring);

                                if (!palabrasDesc.contains(palabraInsert.getPalabra())) {
                                    db.agregarPalabra(palabraInsert.getPalabra(),
                                            palabraInsert.getSubtitulo(),
                                            palabraInsert.getSignificado(),
                                            palabraInsert.getAudio_url());
                                    palabrasDesc.add(palabraInsert.getPalabra());
                                }
                            }
                        }
                        br.close();

                        ListaDic = findViewById(R.id.ListaDic);

                        ListaDic.setAdapter(adaptador);

                        // Las organiza alfabéticamente.
                        ordenarAlfabeto();

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Importar")
                                .setMessage("Se ha importado de: \n" + archivo)
                                .setIcon(R.drawable.itl)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        importarToast("");
                                        dialogInterface.dismiss(); // Cerramos el Alert.
                                    }
                                })
                                .create()
                                .show();
                    } catch (IOException ex) {
                        Toast.makeText(this, "ERROR: " + ex,
                                Toast.LENGTH_SHORT ).show();
                    }
                } else {
                    Toast.makeText(this,
                            "El almacenamiento externo no esta habilitado para la LECTURA",
                            Toast.LENGTH_SHORT ).show();
                }
                return true;
            }

            case R.id.item3:
            {
                if ( isAlmExtEscribible() ) {
                    // Asociamos un File para el archivo en la ruta anterior
                    File archivo = new File(rutaArchivo, nombreText);
                    StringBuffer lista = new StringBuffer();

                    // Crea diferentes ArrayList para guardar el nombre, subtitulo, descripcion, y audio.
                    DataBase db = new DataBase(getApplicationContext());
                    ArrayList<Palabra> palabras = db.getPalabras();
                    ArrayList<String>  nombre = new ArrayList<>();
                    ArrayList<String>  subtitulo = new ArrayList<>();
                    ArrayList<String>  palabrasDesc = new ArrayList<>();
                    ArrayList<String>  audio = new ArrayList<>();
                    palabras.forEach((x) -> nombre.add(x.getPalabra()));
                    palabras.forEach((x) -> subtitulo.add(x.getSubtitulo()));
                    palabras.forEach((x) -> palabrasDesc.add(x.getSignificado()));
                    palabras.forEach((x) -> audio.add(x.getAudio_url()));

                    ArrayList<String> imprimir = new ArrayList<>();
                    // Si hay algun valor, lo elimina para volver a escribir.

                    if (imprimir.size() > 1) {
                        imprimir.removeAll(imprimir);
                    }

                    // Si el audio es nulo, lo pone como vacío.
                    for (int i = 0; i < audio.size(); i ++) {
                        if (audio.get(i) == null) {
                            audio.set(i, "");
                        }
                    }

                    // Hace un ciclo para obtener los valores, agregarlos al arreglo de imprimir.
                    for (int i = 0; i < palabras.size(); i ++) {
                        Palabra palabraInsert = new Palabra( nombre.get(i),
                                                             subtitulo.get(i),
                                                             palabrasDesc.get(i) );
                        palabraInsert.setAudio_url(audio.get(i));

                        imprimir.add(nombre.get(i) + "|");
                        imprimir.add(subtitulo.get(i) + "|");
                        imprimir.add(palabrasDesc.get(i) + "|");
                        imprimir.add(audio.get(i) + "|" + "\r\n");
                    }

                    // Hace una lista del arreglo imprimir.
                    for (String s : imprimir) {
                        lista.append(s);
                    }

                    try {
                        BufferedWriter br = new BufferedWriter( new FileWriter( archivo ) );

                        // Imprime en un text.
                        br.write( lista.toString() );
                        br.close();

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Exportar")
                                .setMessage("Se ha exportado en:\n" + archivo)
                                .setIcon(R.drawable.itl)
                                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        exportarToast("");
                                        dialogInterface.dismiss(); // Cerramos el Alert.
                                    }
                                })
                                .create()
                                .show();
                    } catch ( IOException ex ) {
                        Toast.makeText( this, "ERROR: " + ex,
                                Toast.LENGTH_SHORT ).show();
                    }
                    return true;
                }
            }

            case R.id.item4:
            {
                // Muestra información de la carpeta ya asignada para almacenar el audio y el diccionario.txt.
                File rutaMiArchivo = new File( rutaArchivo );
                ArrayList <String> listaArchivos = new ArrayList<> (
                        Arrays.asList( rutaMiArchivo.list() ) );

                AlertDialog.Builder builder = new AlertDialog.Builder(this );
                builder.setTitle( "Información" )
                        .setMessage( "Carpeta de Almacenamiento: " + ruta + "\n"
                                + "Espacio Total (MB): " +
                                ( rutaExt.getTotalSpace() / ( 1024 * 1024 ) + "\n" )
                                + "Espacio Libre (MB): " +
                                ( rutaExt.getFreeSpace() / ( 1024 * 1024 ) + "\n" )
                                + "Lista de archivos en " + rutaArchivo + ":\n" +
                                listaArchivos )
                        .setIcon( R.drawable.itl )
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialogInterface, int i ) {
                                dialogInterface.dismiss(); // Cerramos el Alert.
                            }
                        } )
                        .create()
                        .show();
                return true;
            }

            case R.id.item5:
            {
                // Al tener deshabilitado el botón de atrás, tenemos que usar el botón de salir para cerrar la app.
                AlertDialog.Builder builder = new AlertDialog.Builder(this );
                builder.setTitle( "Cerrar App." )
                        .setMessage( "¿Desea salir de la Aplicación?" )
                        .setIcon( R.drawable.itl )
                        .setNeutralButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) { }
                        } )
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialogInterface, int i ) {
                                finish();
                                System.exit(0);
                            }
                        } )
                        .create()
                        .show();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Son 3 Toast, el primero para mostrar información del proyecto, el segundo para importar y el tercero para exportar.
    public void mostrarToast ( String mensaje ) {
        Toast.makeText( this, "U4 ProyectoApp v3.0", Toast.LENGTH_SHORT ).show();
    }

    public void importarToast ( String mensaje ) {
        Toast.makeText( this, "Se ha importado: " + nombreText, Toast.LENGTH_SHORT ).show();
    }

    public void exportarToast ( String mensaje ) {
        Toast.makeText( this, "Se ha exportado: " + nombreText, Toast.LENGTH_SHORT ).show();
    }