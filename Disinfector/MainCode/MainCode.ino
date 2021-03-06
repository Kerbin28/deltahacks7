//#include <Servo.h>
#include <NewPing.h>

const int sensorPin = A0;    // select the analog input pin
const int ledPin = 3;       // select the pin for the output
const int ledpurple = 5; //refill indicator led pin
const int button = 6; 
const int MAXCOUNT = 50;
const int Motorpin = 9;
//const int MAXposition = 120;
//const int MINposition = 30;
const int ECHO_PIN = 11;
const int TRIGGER_PIN = 12;
const int MAX_PEOPLE = 10;

int sensorValue;   // variable to store the value coming from the sensor
bool isOpen = false;
int count = 0;
bool refill = 0;
int people_inside = 0;

//Servo motor;
NewPing sonar(TRIGGER_PIN,ECHO_PIN,200);

void setup () {
  pinMode(ledPin, OUTPUT); //setup output
  pinMode(button, INPUT); 
  pinMode(ledpurple, OUTPUT);
  pinMode(Motorpin, OUTPUT);
  //motor.attach(Motorpin);
  Serial.begin (9600); //setup communication rate
}

void loop () {
  
  sensorValue = analogRead (sensorPin);  //setup variable that stores sensor's return values
  delay(100);

  
  if (!isOpen&&sensorValue>100) //Detect if the door is opened
  {
    isOpen = true;
  }
  else if(isOpen&&sensorValue<50) //Detect if the door got closed
  {
    if(refill == 0)
    {
      count++;
      delay(2000);
      Disinfect();
    }
    isOpen = false;
  }
  if (count == MAXCOUNT){
    digitalWrite(ledpurple, HIGH);
    refill = 1;
  }

  if(refill == 1 && digitalRead(button) == HIGH)
  {
    delay(100);
    count = 0;
    refill = 0;
    digitalWrite(ledpurple, LOW);
  }

  Serial.println (sensorValue, DEC); //prints output values from sensor
  

  Serial.println(sensorValue); 
  delay (100);
}

void Disinfect()
{
    digitalWrite(ledPin, HIGH); 
    digitalWrite(Motorpin,HIGH); 
    delay(500);
    digitalWrite(Motorpin,LOW);
    digitalWrite (ledPin, LOW);
}
