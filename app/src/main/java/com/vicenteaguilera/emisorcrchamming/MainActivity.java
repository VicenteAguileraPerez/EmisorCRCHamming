package com.vicenteaguilera.emisorcrchamming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String dato,divisior,tramaHammingCrc;
    boolean par_o_impar;
    TextView textView_decimal;
    EditText editText_entrada,editText_divisor,editText_salida;
    RadioButton radioButton_par,radioButton_impar;
    Button button_generar,button_limpiar;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_entrada = findViewById(R.id.editText_entrada);
        editText_divisor = findViewById(R.id.editText_divisor);
        editText_salida = findViewById(R.id.editText_trama);
        radioButton_par = findViewById(R.id.radioButton_par);
        textView_decimal = findViewById(R.id.textView_decimal);

        radioButton_impar = findViewById(R.id.radioButton_impar);
        button_generar = findViewById(R.id.button_generar);
        button_limpiar = findViewById(R.id.button_limpiar);
        radioGroup = findViewById(R.id.radioGroup);

        button_limpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                editText_entrada.setText("");
                editText_divisor.setText("");
                editText_salida.setText("");
                radioGroup.clearCheck();
                textView_decimal.setText("");

            }
        });

        button_generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dato = editText_entrada.getText().toString();
                divisior=editText_divisor.getText().toString();
                if(evaluarEntrada(dato) )
                {
                    if(isNumeric(editText_divisor.getText().toString()))
                    {
                        if(isBinario(Integer.parseInt(divisior)) && Integer.parseInt(divisior)!=0)
                        {

                            if(radioButton_par.isChecked())
                            {
                                Log.e("b",convertToBinario((dato.charAt(0)))+" "+ ((int)dato.charAt(0)));
                                par_o_impar=true;
                                CRC(Hamming((convertToBinario(dato.charAt(0)))));//
                                textView_decimal.setText("Unicode: "+((int)dato.charAt(0))+"");
                            }
                            else if(radioButton_impar.isChecked())
                            {
                                par_o_impar=false;
                                Log.e("b",convertToBinario((dato.charAt(0)))+"");
                                CRC(Hamming((convertToBinario(dato.charAt(0)))));
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this,"Seleccione la paridad",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"No es un binario el divisor o no es diferente de 0",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Debe de introducir divisor valido ejemplo 1010",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Debe introducir solo un caracter",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }




    private String[] Hamming(String binario)
    {

        String hammingTrama[] = new String[12];
        /**
         * Coloca los datos en donde no hay potencias de 2 en el vector de hamming
         */
        //0 0 0 0 0 0 0 0 0 0 0 0
        //1 2 3 4 5 6 7 8 9 10 11 12
        int cont_digitos=0;
        for (int i=0;i<hammingTrama.length;i++)
        {
            int x =i+1;
            if((x != 0) && ((x & (x - 1)) == 0))//es potencia de 2
            {
                //Log.e("b","Soy paridad");
                hammingTrama[i]="0";
                //Log.e("b",""+hammingTrama[i]);
            }
            else
            {
                //Log.e("b","Soy dato");
                hammingTrama[i]=String.valueOf(binario.charAt(cont_digitos));
                //Log.e("b",""+hammingTrama[i]);
                cont_digitos++;
            }

        }
        for(int i=0;i<2;i++)
        {
            for(int j=0;j<2;j++)
            {
                if(i==0 && j==0)
                {
                    //vector de 5
                    String paridades = hammingTrama[2]+hammingTrama[4]+hammingTrama[6]+hammingTrama[8]+hammingTrama[10];
                    hammingTrama[0]=String.valueOf(Paridades(paridades));
                    Log.d("b","vector 1");
                    Log.d("b",hammingTrama[0]+"");

                }
                else if(i==0 && j==1)
                {
                    //vector de 5
                    String paridades = hammingTrama[2]+hammingTrama[5]+hammingTrama[6]+hammingTrama[9]+hammingTrama[10];
                    hammingTrama[1]=String.valueOf(Paridades(paridades));
                    Log.d("b","vector 2");
                    Log.d("b",hammingTrama[1]+"");
                }
                else if(i==1 && j==0)
                {
                    //vector de 4
                    String paridades = hammingTrama[4]+hammingTrama[5]+hammingTrama[6]+hammingTrama[11];
                    hammingTrama[3]=String.valueOf(Paridades(paridades));
                    Log.d("b","vector 3");
                    Log.d("b",hammingTrama[3]+"");
                }
                else
                {
                    //vector de 4
                    String paridades = hammingTrama[8]+hammingTrama[9]+hammingTrama[10]+hammingTrama[11];
                    hammingTrama[7]=String.valueOf(Paridades(paridades));
                    Log.d("b","vector 4");
                    Log.d("b",hammingTrama[7]+"");
                }
            }
        }

        return hammingTrama;

    }

    private boolean isBinario(int divisior)
    {
        boolean bandera = true;
        for(char e : String.valueOf(divisior).toCharArray())
        {
            if(e!='1' && e!='0')
            {
                bandera = false;
                break;
            }
        }
        return bandera;
    }

    private String convertToBinario(int dec)
    {
        String dato="";
        if(dec>=128)
        {
            dato+="1";
            dec-=128;
        }
        else
        {
            dato+="0";
        }
        if(dec>=64)
        {
            dato+="1";
            dec-=64;
        }
        else
        {
            dato+="0";
        }
        if(dec>=32)
        {
            dato+="1";
            dec-=32;
        }
        else
        {
            dato+="0";
        }
        if(dec>=16)
        {
            dato+="1";
            dec-=16;
        }
        else
        {
            dato+="0";
        }
        if(dec>=8)
        {
            dato+="1";
            dec-=8;
        }
        else
        {
            dato+="0";
        }
        if(dec>=4)
        {
            dato+="1";
            dec-=4;
        }
        else
        {
            dato+="0";
        }
        if(dec>=2)
        {
            dato+="1";
            dec-=2;
        }
        else
        {
            dato+="0";
        }
        if(dec>=1)
        {
            dato+="1";
            dec-=1;
        }
        else
        {
            dato+="0";
        }
        return dato;
    }

    private boolean isNumeric(String divisor)
    {
        try {
            Integer.parseInt(divisor);
            return true;
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }

    }
    private boolean evaluarEntrada(String entrada)
    {
            return (entrada.length()==1);
    }
    private int Paridades(String vec)
    {
        int cant_unos=0;
        int ret0_1;
        for(int i=0;i<vec.length();i++)
        {
            if(vec.charAt(i)=='1')
            {
                cant_unos++;
            }
        }
        Log.e("b",vec+"unos="+cant_unos);
        if(par_o_impar)
        {
            ret0_1=(cant_unos%2==0)?0:1;
        }
        else
        {
            ret0_1=(cant_unos%2==0)?1:0;
        }
        return  ret0_1;
    }
    private void CRC(String[] hamming)
    {
        String bitCRC=creaBitCrc(divisior.length());
        tramaHammingCrc=eliminarCerosIzquierda(hamming)+bitCRC;

        String res_xor="";
        String aux = tramaHammingCrc.substring(0,divisior.length());
        Log.e("b",tramaHammingCrc.length()+" "+tramaHammingCrc+" "+aux);
        int i = aux.length();
        do {
        for (int j = 0; j < divisior.length(); j++) {
            res_xor += xOr(String.valueOf(aux.charAt(j)), String.valueOf(divisior.charAt(j)));
        }
        res_xor = eliminarCerosIzquierda(res_xor);
        while (res_xor.length() < divisior.length() && i!=tramaHammingCrc.length())
        {
            res_xor += String.valueOf(tramaHammingCrc.charAt(i));
            res_xor = eliminarCerosIzquierda(res_xor);
            i++;
        }
        aux = res_xor;
        res_xor = "";
    }while (i<tramaHammingCrc.length());

        if(aux.length()==0)
        {
            //salio vacía significa que retorno 0's
            aux=creaBitCrc(divisior.length());
            editText_salida.setText(agregarCerosIzquierda(hamming)+aux);
        }
        else if(aux.length()==divisior.length() && aux.charAt(0)!='0')
        {
            //dividir una vez más
            for (int j = 0; j < divisior.length(); j++)
            {
                res_xor += xOr(String.valueOf(aux.charAt(j)), String.valueOf(divisior.charAt(j)));
            }
            aux=res_xor;
            if(aux.length()==divisior.length())
            {
                editText_salida.setText(agregarCerosIzquierda(hamming)+aux.substring(1));
            }else
            {
                editText_salida.setText(agregarCerosIzquierda(hamming)+aux);
            }
        }
        else
        {
            //es el valor final

            if(aux.length()==divisior.length()-1)
            {

                editText_salida.setText(agregarCerosIzquierda(hamming)+aux);
            }
            else
            {

                String ceros= agregarCeros(divisior.length()-aux.length());
                aux=ceros+aux;
                editText_salida.setText(agregarCerosIzquierda(hamming)+aux.substring(1));


            }

        }


    }

    private String eliminarCerosIzquierda(String res_xor)
    {

        String datos="";
        if(Integer.parseInt(res_xor)==0)
        {
            return datos;
        }
        else
        {
            datos=String.valueOf(Integer.parseInt(res_xor));
            return datos;
        }
    }


    private String xOr(String a, String b)
    {
        return a.equals(b)?"0":"1";
    }
    private String creaBitCrc(int divisiorLenght)
    {
        String bitCrc="";
        for(int i=0;i<divisiorLenght-1;i++)
        {
            bitCrc+="0";
        }
        return bitCrc;
    }
    private String eliminarCerosIzquierda(String[] vector)
    {
        boolean bandera_elimina_ceros_izquierda=false;
        String datos="";

        for (int i=0;i<vector.length;i++)
        {
            if (vector[i].equals("0") && !bandera_elimina_ceros_izquierda)
            {
                continue;
            }
            else {
                bandera_elimina_ceros_izquierda = true;
                datos += vector[i];
            }
        }
        return datos;
    }
    private String agregarCerosIzquierda(String[] vector)
    {
        String datos="";

        for (int i=0;i<vector.length;i++)
        {

            datos += vector[i];

        }
        return datos;
    }
    private String agregarCeros(int Lenght)
    {
        String bitCrc="";
        for(int i=0;i<Lenght;i++)
        {
            bitCrc+="0";
        }
        return bitCrc;
    }
}

