/*================================================
 * VARIABLES, DEFINICIONES
=================================================*/
/* Librerías */
//#include <Adafruit_Sensor.h>
#include <DHT.h>    // librería del sensor de temperatura y humedad
#include <DHT_U.h> // librería del sensor de temperatura y humedad

/*================================================*/
/* Pines */
#define DHTPIN 2       // define el pin de datos del sensor de temperatura y humedad
#define DHTTYPE DHT11 // define el tipo de dispositivo medidor que estemos utiliando
#define PIR 4        // define el pin de datos del sensor de movimiento
#define LED 5       // define el pine para encender el led

DHT dht(DHTPIN, DHTTYPE); // Establecemos el sensor el sensor DHT11

/*================================================*/
/* Variables */
unsigned long t0 = 0;
unsigned long t1 = 0;
unsigned long tS = 0;

float varTemp = 0.0, varHum = 0.0; // variables que sirven de control en caso de que la temperatura no se modifique

/*================================================*/
/* Wifi */
String ssid = "ssid";
String pass = "pass"; 

/*================================================*/
/* BBDD */
// Sustitución en la conexión
// "" -> %20
// '' -> %27

// Nombre de la BBDD, usuario y contraseña
const char* dbName = "prmedicapp";
const char* dbUser = "prjliebana_admin";
const char* dbPass = "*medicapp*2";

// Almacena el envio GET y las variables
const unsigned int TAM_BUFF = 500;
char cmd[TAM_BUFF];
const unsigned int TAM_BUFF1 = 500;
char cmd1[TAM_BUFF1];

// debugger - habilitar los false
bool waitOk(long, bool = false, bool = true);
int waitAnswer(long, bool = false, bool = true);
void sendCommand(String, bool = true);

/*================================================
 * INICIO DEL SISTEMA
=================================================*/
void setup() {
  Serial.begin(115200);  // inicializa comunicación con PC
  Serial1.begin(115200);// Serial de wifi

  pinMode(PIR, INPUT);    // Establecemos PIR como entrada de datos  
  pinMode(LED, OUTPUT);    // Establecemos el LED como salida

  dht.begin(); // Inicializamos el sensor DHT
  
  t0 = millis();
}

/*================================================*/
/* CONFIGURACIÓN DEL WIFI Y CONEXIÓN A LA BBDD*/

/* 
 * Configuración del wifi
 */
void wifiConf(){
  String cad;

  // Módulo Wifi AT. Para comunicarte por el monitor serie con el módulo wifi
  // Permite introducir comandos AT
  /*while (millis() < 50000){
    if(Serial.available()){
      Serial1.write(Serial.read());
    }
    if(Serial1.available()){
      Serial.write(Serial1.read());
    }
  }*/
  
  sendCommand("AT");
  if(!waitOk(1000)){
    Serial.println("Error: Módulo Wifi no responde");
    return;  
  }

  // En caso de estar ya vinculado al wifi te manda la dirección ip asignada al módulo y la puerta de enlace
  // sendCommand("AT+CIPSTA?");
  // waitOk(4000);

  /*
  sendCommand("AT+RST");
  printResponse(2000);
  sendCommand("AT+CIPSTATUS");
  printResponse(6000);
  */
  
  /*
  sendCommand("AT+RST");
  printResponse(2000);

  // sendCommand("AT+CWMODE=1"); // establece el módulo en modo cliente
  // waitOk(2000);
  cad = "AT+CWJAP=\"" + String(ssid) + "\",\"" + String(pass) + "\"";
  sendCommand(cad);
  waitOk(10000);
  sendCommand("AT+CIPSTA?");
  waitOk(4000);
  
  sendCommand("AT+CIFSR");
  if(!waitOk(3000)){
    Serial.println("Wifi no conectada a la Red");
    return;
  }
  
  sendCommand("AT+CIPSTATUS");
  printResponse(6000);

  /* Comandos para el módulo AT - Conexión por wifi
   * AT+CWMODE=1 // 1=Cliente, 2=Punto de Acceso, 3=Ambos
   * AT+CWLAP // listar wifis
   * AT+CWDHCP=1,0 // activar dch 1=client 0=enabled
   * AT+CIPSTA="192.168.1.xx" // establecerle una ip si no se conecta
   * AT+CWJAP="ssid","pass" // conectarse a la wifi
   */
}

/* 
 * Conexión con la BBDD remota 
 */
void bbdd(){
  String cad;
  
  cad = "AT+CIPSTART=\"TCP\",\"2.139.176.212\",8888";// AT+CIPSTART - Conexión a una máquina
  sendCommand(cad);
  waitOk(2000);
  sendCommand("AT+CIPSTATUS");
  waitOk(1000);
}

/* 
 * Respuesta del servidor
 * Nos dice si la inserción de los datos ha sido correcta
 */
void respond(){
  int respondTime;
  respondTime = waitAnswer (10000);
  
  switch(respondTime){
    case 0:
      Serial.println("Inserción correcta");
      break;
    case 1:
      Serial.println("Error 1");
      break;
    case 2:
      Serial.println("Error 2");
      break;
    default:
      Serial.println("Not Found");
      sendCommand("AT+CIPCLOSE");
      break;
  }  

  Serial.println("Terminado");
  //for(;;);
}

//
void sendCommand(String c, bool echo){
  Serial1.flush();
  Serial1.println(c);
  if(echo)Serial.println("***********************************************");
  if(echo)Serial.println(c);
  Serial1.flush();
}

// 
void sendGet(String cmd){
  String cad = "AT+CIPSEND=" + String(cmd.length() + 4);
  sendCommand(cad);
  waitOk(500);
  sendCommand(cmd, true);
  sendCommand("");
}

//
void printResponse(long timeoutamount){
  unsigned long timeout = millis() + timeoutamount;
  unsigned long timeStart = millis();
  char c;
  Serial.println("-----------------------------------------------");
  while(millis() <= timeout){
    if (Serial1.available() > 0){
      c = Serial1.read();
      Serial.write(c);  
    }
  }
}

/* 
 * Tiempo para realizar las tareas
 * Espera a la contestación del servidor que sea solo OK 
 */
bool waitOk(long timeoutamount, bool echo, bool echoError){
  static int code = 0;
  unsigned long timeout = millis() + timeoutamount;
  unsigned long timeStart = millis();
  char c0 = ' ', c1 = ' ';
  code = code + 1;
  if(echo)Serial.println("-----------------------------------------------");
  while (millis() <= timeout){
    while (Serial1.available() > 0){
      c1 = Serial1.read();
      
      if(echo)Serial.write(c1);
      if(c0 == 'O' && c1 == 'K'){ 
        if(echo)Serial.println("\nwaitOk TRUE [" + String(millis() - timeStart) + "ms]");
        delay(100);
        while(Serial1.available() > 0) Serial1.read();
        return true;
      }
      c0 = c1;
    }
  }
  if(echoError)Serial.println("\nwaitOk False (Code: " + String(code) + ") [" + String(millis() - timeStart) + "ms]");
  return false;
}

//
int waitAnswer(long timeoutamount, bool echo, bool echoError){
  unsigned long timeout = millis() + timeoutamount;
  unsigned long timeStart = millis();
  char c[7] = "  ";
  if(echo)Serial.println("-----------------------------------------------");
  while(millis() <- timeout){
      while(Serial1.available() > 0){
        c[6] = Serial1.read();
        
        if(echo)Serial.write(c[6]);
        if(c[6] == '#'){
          if(c[0] == 'E' && c[1] == 'R' && c[2] == 'R' && c[3] == 'O' && c[4] == 'R'){
            Serial.println("\nAnswer ERROR: " + String(c[5]) + " [" + String(millis() - timeStart) + "ms]");
            delay(100);
            while(Serial1.available() > 0) Serial1.read();
            return (c[5] = '0');
          }
          if(c[4] == 'O' && c[5] == 'K'){
            Serial.read();
            char code = Serial1.read();
            if(echo)Serial.println("\nAnswer Ok [" + String(millis() - timeStart) + "ms]");
            delay(100);
            while(Serial1.available() > 0) Serial1.read();
            return 0;
          }
        }
        c[0] = c[1]; c[1] = c[2]; c[2] = c[3]; c[3] = c[4]; c[4] = c[5]; c[5] = c[6];
      }
  }
  if(echoError)Serial.println("\nwaitAnswer False [" + String(millis() - timeStart) + "ms]");
  return -1;
}

/*================================================*/
/* SENSORES */
/* 
 * Sensor de movimiento 
 */
void pir(){ 
  if(digitalRead(PIR) == HIGH){
    digitalWrite(LED, HIGH);
    delay(3000);
    Serial.println("Se ha detectado movimiento.");
    Serial.print("\n");

    // conexión a la BBDD 2.139.176.212:8888
    bbdd();
    
    // Envia datos a la BBDD
    // URL del navegador: 2.139.176.212:8888/insert.php?db=prmedicapp&user=prjliebana_admin&pass=*medicapp*2&insert=temperatura(TEMPERATURA,HUMEDAD)VALUES(%s,%s)
    const char* format = "GET /insert.php?db=%s&user=%s&pass=%s&insert=%s"; // %s es sustituido por las variables almacenadas en snprintf
    const char* insertCmd = "movimiento(SENSOR)VALUES(%27Puerta%20exterior%27)"; // %s es sustituido por las variables almacenadas en snprintf
    snprintf(cmd, TAM_BUFF, format, dbName, dbUser, dbPass, insertCmd); // variables de format
    
    Serial.println(cmd);
    
    sendGet(cmd);
    respond(); 
   
  }else{
    digitalWrite(LED, LOW);  
  }  
}

/*
 * Sensor de temperatura y humedad
 * @t -> temperatura que recibe del sensor
 * @h -> humedad que recibe del sensor
 * isnan(t) || isnan(h): Solo lee los datos del sensor si son válidos
 * Si la lectura es correcta, los imprime por consola 
 * se conecta con la BBDD y manda un GET con el nombre de la BBDD, usuario, password y datos a insertar
 */
void temp(float t, float h){
  String cad;
  char ct[8];
  char ch[8];
  char cth[4]; 

  if (isnan(t) || isnan(h)){ // Comprobamos si ha ha habido error en la lectura
      Serial.println("Error en la recogida de datos del sensor DHT11");
      return;
  }else{   
      varTemp = t; // actualiza la temperatura
      varHum = h; // actualiza la humedad
      float ic = dht.computeHeatIndex(t, h, false); // calcula el índice de calor en grados centígrados basándose en la temperatura y humedad
     
      Serial.print("Tiempo transcurrido des de la última actualización: ");
      Serial.print(tS);
      Serial.println(" segundos.");
      // Impresión de datos
      Serial.print("Temperatura: ");
      Serial.print(t);
      Serial.print(" | ");
      Serial.print("Humedad: ");
      Serial.print(h);
      Serial.print(" | ");
      Serial.print("Índice de calor: ");
      Serial.println(ic);
      Serial.print("\n");
      // float to char
      dtostrf(t, 4, 2, ct);
      dtostrf(h, 4, 2, ch);
    
      // conexión a la BBDD 2.139.176.212:8888
      bbdd();
      // Envia datos a la BBDD
      // URL del navegador: 2.139.176.212:8888/insert.php?db=prmedicapp&user=prjliebana_admin&pass=*medicapp*2&insert=temperatura(TEMPERATURA,HUMEDAD)VALUES(%s,%s)
      const char* format = "GET /insert.php?db=%s&user=%s&pass=%s&insert=%s"; // %s es sustituido por las variables almacenadas en snprintf
      const char* insertCmd = "temperatura(TEMPERATURA,HUMEDAD)VALUES(%s,%s)"; // %s es sustituido por las variables almacenadas en snprintf
      snprintf(cmd1, TAM_BUFF1, insertCmd, ct, ch); // variables de insertCMD
      snprintf(cmd, TAM_BUFF, format, dbName, dbUser, dbPass,cmd1, TAM_BUFF1, insertCmd); // variables de format
      
      Serial.println(cmd);
      sendGet(cmd);
      respond(); 
  }
}

void loop() {
  // Variables 
  t1 = millis(); // contador que permite leer la temperatura y humedad después de un tiempo establecido
  float t = dht.readTemperature(); // lectura de la temperatura del sensor en grados centígrados
  float h = dht.readHumidity(); // lectura de la humedad relativa del sensor

  delay(2000);
  
  // Configuración del wifi
  wifiConf(); 

  // Lectura del sensor de temperatura/humedad al pasar un determinada tiempo y una variación en la temperatura/humedad
  if ((t0 == 0 || t1 > (t0+60000)) && (t > varTemp || t < varTemp || h > varHum || h < varHum) ){ // comprobamos si ha habido alguna variación en la temperatura
    t0 = millis();
    temp(t, h);
  }

  // Lectura del sensor de movimiento
  pir();  
}
