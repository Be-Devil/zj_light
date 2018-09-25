#include <ArduinoJson.h>
#include <ESP8266WiFi.h>     
#include <String.h>
WiFiClient client;

char ssid[30] = "init";//默认热点名称，可修改为自己的路由器用户名
char password[30] = "12345678";///默认热点密码，可修改为自己的路由器密码
char onenettcp[20] = "192.168.43.1";//热点默认ip
int  onenetport = 1811;//app默认端口号
String null = "";
int current_Inf=0;
int next_Inf=0;
char buf[30];
String line = "";
char s1;
char s2;

int lastTime;
int timer = 0;
int current_timer = 0;
int i = 0;
int flag_bz = 0;
int SR_2;    //右边红外避障传感器状态
int SL_2;    //左边红外避障传感器状态
String val;

void connectWifi() {
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED){
    delay(1000);
    Serial.print(".");   
  }
  Serial.println("\rWifi Connected\r");
}

void c_send(char *s) {
     client.write((const uint8_t*)s, sizeof(s));
}

void LoupInit() {
  while (!client.connected())
  {
//    Serial.println("Reconnect TCP Server...");
    if (!client.connect(onenettcp, onenetport)){
      if (WiFi.status() != WL_CONNECTED){
//        Serial.println("Reconnect WIFI ...");
        connectWifi();
      }
      delay(100);
    } else {
//      Serial.println("Reconnect Falied");
    }
  }
}

void Init() {
  connectWifi();
  if (client.connect(onenettcp, onenetport)) {
//          Serial.println("Client Connected\r"); 
  }
}

void jieshou() {
    if(client.available()){
      null.toCharArray(buf,30);
      while (client.available()) {
        buf[i] = client.read();
        Serial.print(buf[i],HEX);
        Serial.print(" ");
        if(buf[i] == 0x2a&&buf[1] == i-1){//&&buf[1] == i-2&&buf[0] == 0xaa
          break;
          }
        i++;
        
      }
    Serial.println("");
    }
}


void setup() {
  Serial.begin(9600);
  
}

void loop() { 
  Init();
  while(1){
        LoupInit();
        while(client.available())
          Serial.print((char)client.read());
 
  }
  
}
