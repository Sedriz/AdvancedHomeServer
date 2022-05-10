#!/bin/sh

# Usage: ersetzt in allen Dateien, die mit Doppelpunkten getrennt in der Umgebungsvariablen REPLACE_FILES definiert sind, Platzhalter im
# Format `{{NAME}}` mit den Werten gleichnamiger Umgebungsvariablen und führt anschließend alle Kommandozeilenparameter als Kommando aus

# ######### #
# Beispiel: #
# ######### #

# Umgebungsvariablen:
# - LOREM='ipsum'
# - REPLACE_FILES='/test.txt'

# Inhalt von /test.txt vor Ausführung:
# Der Platzhaltertext ist: {{LOREM}}

# Kommando:
# cd / && /env-replace-exec.sh pwd

# Ausgabe:
# /

# Inhalt von /text.txt nach der Ausführung:
# Der Platzhaltertext ist: ipsum

# Einsatzszenario:
# Manche Docker-Container bieten keine Konfiguration über Umgebungsvariablen an. Mit diesem Skript kann man z.B. Umgebungsvariablen in
# Konfigurationsdateien austauschen bevor man den eigentlichen ENTRYPOINT aufruft.

replace() {
	IFS=$(printf ' \t\n#'); IFS="${IFS%#}"
	for var in $(env | grep -o "^[A-Z][A-Z_0-9]*="); do
		var="${var%=}"
		[ "$var" = "REPLACE_FILES" ] && continue
		if grep "$var" "$1" >/dev/null; then
			val=$(eval "echo \$$var")
			sed -Ei "s|\{\{$var\}\}|$val|" "$1"
		fi
	done
}

if [ -n "$REPLACE_FILES" ]; then
	IFS=':'
	for file in $REPLACE_FILES; do
		if [ -f "$file" ]; then
			replace "$file"
		fi
	done
fi

if [ $# -gt 0 ]; then
	"$@"
fi
