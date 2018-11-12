# StandAloneSerialMonitor
A standalone serial monitor useful with arduino that needs just java jre to be used.

## Usage
It has two main usages:
###GUI usage (work in progress): 
    Just launch the software without any parameter

###CLI usage:
    * *-l*: it prints the list of available ports
    * *-r <port> [baudrate]*: it reads and prints to console, port is mandatory while baudrate can be omitted. (9600 if not specified)
    * *-f <nomeFile>*: used with *-r...* parameter it reads from specified port (and baudrate) and logs everything to specified file
