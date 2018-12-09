# StandAloneSerialMonitor
A standalone serial monitor useful with arduino that needs just java jre and jssc to be used.

## Usages
*	*no flag* or *--interactive*: GUI usage
*	*-h*: for the flag list
*   *-l*: for the list of available ports
*   *-r <port> [baudrate]*: reads and prints to console, port is mandatory while baudrate can be omitted (9600 default)
*	*-f <nomeFile>*: used with *-r* parameter reads from specified port (and baudrate) and logs everything to specified file