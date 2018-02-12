#Fisa cerintelor

###Layere principale:
    1. Client App
    2. Server App
    3. Device Manager (? integrat in server app)
    4. Simple device driver
        - current status
        - operatii permise
        - device type

###Scenarii de utilizare
> #####View
    Utilizatorii vor avea un cont, se vor loga si vor putea vedea dispozitivele personale.
---    
> #####Device management panel
    1. CRUD operations
    2. Cum vor adauga utilizatorii un nou dispozitiv?
        - conectare dinamica?
        - asociere ip & port - utilizator
        - cum se detecteaza ce tip de dispozitiv e?
    3. Cum se va sterge un nou dispozitiv?
    4. UI cu panou ce contine doar numele dispozitivului
        - la click: status + comenzi disponibile
---
> #####Profile
    1. Informatii personale
    2. CRUD
---
> #####Securitate
    1. Integritatea aplicatiei client
    2. Integritatea aplicatiei server
    3. SSL + Java Sockets

###Ideas list
- Serverul creeaza conectiune cu dispozitivul doar cand userul se logheaza sau are deja conectiunea facuta?
- Cum s-ar comporta serverul daca ar avea multe conectiuni? Multithreading server with pool?