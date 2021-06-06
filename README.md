# Rent A Car

### Članovi tima
* Dedić Irma
* Drkić Lamija
* Gotovac Goran

### Opis projekta
Rent a car aplikacija je softversko rješenje koje realizuje proces iznajmljivanja vozila. Akteri ovog softvera su admin, neregistrovani i registrovani korisnik (klijent). 
Svim korisnicima aplikacije bez obzira na ulogu je omogućen pregled svih vozila agencije (uključujući i pregled vozila po kategorijama), pregled detaljnijih specifikacija 
o vozilu koje ih zanima, pregled recenzija i najčešće postavljanih pitanja i odgovora koji mogu pomoći pri donošenju odluka. Admin će nakon uspješne prijave na sistem moći 
da vodi evidenciju o voznom parku agencije, da ima uvid u rezervacije, recenzije, da odgovara na pitanja koja postavljaju klijenti. Dodatno, registrovanim korisnicima 
(klijentima) se nakon uspješne prijave na sistem nudi mogućnost da rezervišu željeno vozilo, obrišu rezervaciju ukoliko se predomisle, postave pitanje o nečemu što ih zanima 
i ostave recenziju, odnosno komentar na neku uslugu. Sistem će slati odgovarajuće notifikacije kako bi korisnici bili informisani o dešavanjima.

### Servisi
* Eureka-service
* API-Gateway
* System-events (gRPC)
* Config-server
* User-Microservice
* Vehicle-Microservice
* ClientCare-Microservice
* Notification-Microservice
* Frontend (Angular 12)

### Potrebno za pokretanje bekenda
* Java verzija 15
* MySql baza podataka

### Potrebno za pokretanje frontenda
* npm install @angular/cli
* npm install
* ng serve

### Upustvo za pokretanje projekta preko Dockera
Za build i pokretanje svih servisa potrebno je pokrenuti skriptu containers.cmd

### Demo snimci
https://drive.google.com/drive/u/1/folders/1s6UmccihNulB2REnBkN-smLGE2jKdxxZ
