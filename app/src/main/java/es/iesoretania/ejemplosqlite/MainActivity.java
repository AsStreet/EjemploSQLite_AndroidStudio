package es.iesoretania.ejemplosqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText edit_codigo, edit_producto, edit_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_codigo = (EditText)findViewById(R.id.et_codigo);
        edit_producto = (EditText)findViewById(R.id.et_producto);
        edit_precio = (EditText)findViewById(R.id.et_precio);
    }

    public void Registrar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edit_codigo.getText().toString();
        String producto = edit_producto.getText().toString();
        String precio = edit_precio.getText().toString();

        if (!codigo.isEmpty() && !producto.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("producto",producto);
            registro.put("precio",precio);

            BaseDeDatos.insert("articulos", null, registro);

            BaseDeDatos.close();

            edit_codigo.setText("");
            edit_producto.setText("");
            edit_precio.setText("");

            Toast.makeText(this, "Producto guardado correctamente", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Debes introducir todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Buscar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getReadableDatabase();

        String codigo = edit_codigo.getText().toString();

        if (!codigo.isEmpty()){
            Cursor fila =BaseDeDatos.rawQuery
                    ("select producto, precio from articulos where codigo ="+codigo,
                            null);

            if (fila.moveToFirst()){
                edit_producto.setText(fila.getString(0));
                edit_precio.setText(fila.getString(1));

                BaseDeDatos.close();
            } else{
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Debes introducir el código del producto", Toast.LENGTH_SHORT).show();
        }
    }

    public void Modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edit_codigo.getText().toString();
        String producto = edit_producto.getText().toString();
        String precio = edit_precio.getText().toString();

        if (!codigo.isEmpty() && !producto.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("producto",producto);
            registro.put("precio",precio);

            int cantidad = BaseDeDatos.update(
                    "articulos", registro, "codigo="+codigo, null);

            BaseDeDatos.close();

            if (cantidad ==1){
                Toast.makeText(this, "Registro modificado correctamente", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Debes introducir todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void Eliminar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(
                this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = edit_codigo.getText().toString();

        if (!codigo.isEmpty()){
            int cantidad = BaseDeDatos.delete(
                    "articulos", "codigo="+codigo, null);

            BaseDeDatos.close();

            if (cantidad ==1){
                Toast.makeText(this, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "No existe el producto", Toast.LENGTH_SHORT).show();
            }
        }  else{
            Toast.makeText(this, "Debes introducir todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}
