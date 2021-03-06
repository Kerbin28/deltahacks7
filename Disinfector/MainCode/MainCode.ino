#include <LiquidCrystal_I2C.h>
//#include <Servo.h>
#include <NewPing.h>
#include <Wire.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

int sensorPin = A0;    // select the analog input pin
int ledPin = 3;       // select the pin for the output
int sensorValue;   // variable to store the value coming from the sensor
int ledpurple = 5;
int button = 6;
const int MAXCOUNT = 5;
int Motorpin = 2;
int spraypin = 7;
const int ECHO_PIN = 11;
const int TRIGGER_PIN = 12;
const int MAX_PEOPLE = 10;

NewPing sonar(TRIGGER_PIN,ECHO_PIN,200);
 
void setup () {
  pinMode (ledPin, OUTPUT); //setup output
  pinMode (ledpurple, OUTPUT); //setup output
  pinMode (button, INPUT); //setup output
  pinMode(Motorpin, OUTPUT);
  pinMode(spraypin, OUTPUT);

  Serial.begin (9600); //setup communication rate
  
  lcd.begin();
  lcd.backlight();
  lcd.clear();
  lcd.println("Hello and Welcome!");
  delay(500);
  lcd.clear();
  lcd.println("Setting up...");
  delay(500);
  lcd.clear();
  PrintLCD(1);
  SendState(1);
}

int buttonState = 0;
bool isOpen = false;
int count = 0;
bool refill = 0;
int PersonCount = 0;

 void loop () {
  int distance = sonar.ping_cm();
  Serial.println(distance);
  sensorValue = analogRead (sensorPin);  //setup variable that stores sensor's return values
  delay(100);
  if (!isOpen&&sensorValue>100) //Detect if the door is opened
  {
    isOpen = true;
  }
  else if(isOpen&&sensorValue<50) //Detect if the door got closed
  {
    delay(500);
    int initial_dist = sonar.ping_cm();   //find the initial distance after the door is closed
    delay(100);
    int final_dist = sonar.ping_cm();     //find the final distance after the door is closed
    if(refill == 0)
    {
      PrintLCD(2);
      SendState(2);
      count++;
      delay(1300);
      Disinfect();
    }
    isOpen = false;
    if ((abs(initial_dist-final_dist)>5))  //The logic behind this is that if the door is closed, and we check that there is someone on the outside,
    {                                           //that means the person is leaving, otherwise the person is entering
      PersonCount = (PersonCount>0)?(PersonCount-1):0;
      //To prevent the Person count from going below zero
    }
    else
    {
      PersonCount++;
    }
    if (PersonCount > MAX_PEOPLE)
    {
      SendState(4);
      PrintLCD(4); 
    }
    else
    {
      PrintLCD(1);
      SendState(1);
    }
  }
  if (count == MAXCOUNT){
    digitalWrite(ledpurple, HIGH);
    refill = 1;
    PrintLCD(3);
    SendState(3);
  }

  if(refill == 1 && digitalRead(button) == HIGH)
  {
    delay(100);
    count = 0;
    refill = 0;
    digitalWrite(ledpurple, LOW);
    PrintLCD(1);
    SendState(1);
  }
  delay (100);
}

void Disinfect()
{
    digitalWrite(ledPin, HIGH); 
    digitalWrite(spraypin,HIGH); 
    delay(5000);
    digitalWrite(Motorpin,HIGH);
    digitalWrite (spraypin, LOW);
    delay(2000);
    digitalWrite (Motorpin, LOW);
    digitalWrite (ledPin, LOW);
}

void PrintLCD(int state)
{
  lcd.clear();
  lcd.print("People Count:");
  lcd.println(PersonCount);
  lcd.print("State:");
  switch(state)
  {
    case 0:
    lcd.println("OFF");
    break;
    case 1:
    lcd.println("Ready");
    break;
    case 2:
    lcd.println("Spraying");
    break;
    case 3:
    lcd.println("Refill Pls");
    break;
    case 4:
    lcd.println("OVER LIMIT");
    break;
    default:
    lcd.println("ERROR");
  }
}

void SendState(int state)
{
  Serial.print(state);
  Serial.print(" ");
  Serial.print(PersonCount);
  Serial.print(" ");
  Serial.print(MAX_PEOPLE);
  Serial.println(" ");
}
