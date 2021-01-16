# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Iancu Alexandru-Gabriel, 324CD

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

## Implementare

### Clase

Precizare: clasele ce implementeaza Strategy sunt explicate la sectiunea Design 
patterns.

#### Entitati

Sunt 3 clase principale care se ocupa de cele 3 tipuri de entitati: Consumer,
Distributor si Producer. Ele stocheaza informatii aferente entitatii si ajuta
in stabilirea de responsabilitati ce au legatura directa sau indirecta cu
entitatea. Interfata Entity este implementata de aceste 3 clase si ajuta in
abstractizarea codului si extinderea acestuia pentru entitati viitoare.

Mai exista si clase auxiliare care detin informatii relevante care sa fie
folosite de instante ale entitatilor:
  * Contract -> creeaza o legatura intre distribuitor si consumatori, ajuta
distribuitorul sa tina evidenta consumatorilor care sunt abonati la el
  * EntityRegister -> un registru al tuturor instantelor curente de entitati,
este folosit de simulare pentru a accesa entitatile si este folosit de entitati
pentru a face rost de instanele altor entitati
  * MonthlyStat -> stocheaza evidenta dintr-o luna a distribuitorilor abonati la
un anumit producator

#### I/O

Clasa Input stocheaza toate datele de la intrare ce sunt folosite pe parcursul
simularii, simetric clasa Output stocheaza datele de iesire relevante pentru
a fi scrise. Clasa Writer este folosita pentru a scrie instanta obiectului
Output(a simularii curente) in fisierul de iesire. Aceasta se foloseste de un
FileWriterClass ce se regaseste sub diverse functionalitati:
  * FileWriterClassTest -> testeaza corectitudinea programului
  * FileWriterClassStore -> stocheaza in folder-ul /out instanta Output la
terminarea simularii, suprascrie la fiecare rulare fisierul de iesire
  * FileWriterClassStoreComplete -> stocheaza in folder-ul /out instantele
Output la fiecare runda, adauga fara sa suprascrie in fisierul de iesire

Clasa FileWriterClassFactory creeaza instantele de tip FileWriterClass discutate
anterior.

#### Actualizare

Pentru actualizarea entitatilor la fiecare runda ma folosesc de clasa Updater.
Updater se ocupa de actualizarea bugetelor, a contractelor si legaturilor, a
preturilor si a tot ce se modifica intre runde. Aceste actualizari contin si 
schimbarile de la finalul unei runde(pentru runda urmatoare). Schimbarile
dintr-o luna sunt stocate intr-o instanta MonthlyUpdate si tin de noi
consumatori, schimbarile de costuri pentru distribuitori si schimbarile de
cantitati pentru producatori. Ultimele 2 schimbari sunt stocate intr-o instanta
CostChange pentru fiecare instanta de entitate(Producer sau Distributor).

### Flow

#### Simulare

Runda initiala:
  * distribuitorii isi aleg producatorii
  * distribuitorii isi calculeaza pretul
  * se actualizeaza consumatorii
  * se actualizeaza distribuitorii
  * se fac actualizarile pentru runda urmatoare(monthlyUpdates)

Runda intermediara:
  * distribuitorii isi calculeaza pretul
  * se actualizeaza consumatorii
  * se actualizeaza distribuitorii
  * se asigneaza producatorii pentru distrbuitorii care si-au reziliat contractul
in tura precedenta
  * se actualizeaza producatorii
  * se fac actualizarile pentru runda urmatoare(monthlyUpdates)

Runda finala = Runda intermediara fara partea cu monthlyUpdates

#### Intre entitati

Consumatorii sunt strans legati de distribuitori, iar distribuitorii sunt
legati de producatori => nu exista nicio legatura intre consumatori si
producatori

##### Consumator <-> Distributor

Consumatorii si distribuitorii se leaga prin intermediul clasei Contract ce
contine id-ul consumatorului si alte specificatii. Clasa Distributor are
o lista de contracte, prin intermediul acestora poate sa faca rost de id-ul
unui consumator abonat. Cu ajutorul acelui id si folosindu-se de metoda
findEntity din entityRegister poate sa gaseasca instanta consumatorului ce are
acel id. Are nevoie de instanta unui consumator pentru a verifica ca acesta nu a 
intrat in faliment. Prin intermediul listei de contracte poate sa afle ce rata
are de incasat in fiecare luna si de asemenea numarul de consumatori curenti
care se folosesc in calculul pretului.

Consumatorul are un camp distributor ce contine instanta distribuitorului.
Prin intermediul acestei instante simulatorul poate sa verifice daca un
consumator are contract cu cineva. De asemenea un consumator poate sa faca
rost de lista de contracte, unde isi poate gasi propriul contract cu respectivul
distribuitor. Cu ajutorul acelui contract afla ce rata are de platit pe luna
curenta.

##### Distributor <-> Producer

Legatura de la producator la distribuitor se face prin intermediul listei
distributorIds ce contine id-urile distribuitorilor abonati la producator
la momentul curent. Producatorul nu are un buget, asa ca tot ce face este
sa tina evidenta acestor distribuitori in monthlyStats.

Distribuitorul nu are o legatura directa la producator, singura informatie pe
care o foloseste este productionCost pe care il calculez cand ii asignez
producatorii. Legatura se realizeaza indirect prin intermediul design
pattern-ului Observer despre care vorbesc mai pe larg in sectiunea
Observer din Design patterns.

### Elemente de design OOP

Folosesc mostenire la strategii si la fii lui FileWriterClass pentru a avea o 
clasa comuna pe care o sa returnez in metoda create din Factory-ul corespondent.

La strategii incapsulez metoda generala de asignare a producatorilor. Am
metoda selectProducers din clasa EnergyChoiceStrategy ce sorteaza producatorii
pe baza unui comparator primit ca parametru si apoi asigneaza distribuitorului
(param) primii N producatori din lista de producatori(param) pana face rost
de energia necesara. Iar in fii clasei abstracte doar trebuie sa creez o clasa
comparator si sa apelez metoda selectProducers cu comparatorul specific clasei.
Astfel daca vine un alt developer care vrea sa adauge o alta strategie, el
trebuie sa creeze doar un nou comparator si sa apeleze metoda selectProducers
fara sa fie nevoie sa stie ce se afla acolo.

Am aplicat generalizarea prin metoda findEntity din entityRegister ce poate
sa cauta in orice lista de elemente care sunt Entity sau fii de Entity primita
ca parametru. Consumer, Producer si Distributor implementeaza Entity si astfel 
pot sa folosesc aceasta metoda pentru a cauta fie un consumator, fie un 
producator, fie un distribuitor dupa id. Aceasta metoda poate fi folosita si
pentru orice noua entitate adaugata la extinderea aplicatiei, cat timp aceasta
va implementa interfata Entity.

### Design patterns

#### Strategy

Strategiile implementate modeleaza felul in care un distribuitor isi alege
producatorii. Clasele folosite sunt:
  * EnergyChoiceStrategy -> interfata folosita de toate strategiile ce se ocupa
de modul in care isi alege un distribuitor producatorii
  * GreenStrategy -> ii spune instantei de distribuitor care o foloseste ca
trebuie sa prioritizeze producatorii ce folosesc renewables
  * PriceStrategy -> ii spune instantei de distribuitor care o foloseste ca
trebuie sa prioritizeze producatorii ieftini
  * QuantityStrategy -> ii spune instantei de distribuitor care o foloseste ca
trebuie sa prioritizeze producatorii ce ofera cea mai mare cantitate de energie
  * EnergyChoiceStrategyFactory -> creeaza instante de tip EnergyChoiceStrategy

Distribuitorul are o clasa producerStrategy ce contine o constanta ce specifica
tipul de strategie pe care ar trebui sa il foloseasca(constanta din
EnergyChoiceStrategyType). In momentul in care trebuie sa ii asignez unui
distribuitor producatori, creez instanta EnergyChoiceStrategy, corespunzatoare
campului producerStrategy, cu ajutorul EnergyChoiceStrategyFactory. 

Strategiile au o metoda assignProducers ce are ca parametrii o lista de
producatori si un distribuitor. Dupa ce creez instanta EnergyChoiceStrategy,
dau distribuitorul corespunzator ca parametru alaturi de toti producatorii
valabili. Apoi strategia se ocupa de asignarea producatorilor pentru distribuitor.
Asignarea se realizeaza la fel pentru fiecare strategie, diferenta e modul
in care sunt sortati producatorii pentru a fi alesi. Astfel fiecare strategie
are o clasa tip Comparator ce are grija ca producatorii sa fie sortati in modul
reprezentativ pentru strategie.

#### Observer

Pentru a implementa design pattern-ul m-am folosit de clasa Observable si
interfata Observer din pachetul java.util. Cele doua entitati care folosesc
pattern-ul sunt Distributor(Observer) si Producer(Observable). Schimbari asupra
unui distribuitor(inafara de falimentare) nu merita atentia producatorului, insa
este foarte important pentru un distribuitor sa stie cand producatorul isi
schimba oferta(in cazul nostru cantitatea de energie oferita).

In momentul in care schimb cantitatea producatorului la final de luna, apelez
metoda notifyDistributors care ii notifica pe toti distribuitorii abonati ca
producatorul si-a schimbat oferta. Toti distribuitorii apelati isi vor
rezilia contractul si astfel sunt sanse sa nu aleaga acelasi producator in runda
care vine, asa ca golesc lista de observatori a producatorului pentru a nu se 
intampla ca producatorul sa isi anunte un fost abonat in turele viitoare.
Distribuitorul notificat isi seteaza campul boolean needsToChangeProducer la true
pentru ca runda viitoare sa stie ca trebui sa isi aleaga un producator.

Distribuitorii sunt adaugati in lista de observatori a unui producator in
momentul in care il aleg in metoda assignProducers din strategii.

#### Altele

Am folosit design pattern-ul Factory pentru a crea strategiile. Metoda
createEnergyChoiceStrategy primeste o constanta de tip EnergyChoiceStrategyType
ce este folosita in switch pentru a crea strategia corespunzatoare. Metoda
in situatia de fata este folosita exclusiv pentru campul producerStrategy
din Distributor, dar poate fi folosita si in cazul in care se adauga alte
entitati in implementare. 

De asemenea mai este folosit si un FileWriterClassFactory ce creeaza o clasa de
tip FileWriterClass. Metoda create din aceasta primeste modul de scriere ca
parametru drept String si un inputFormat ce este folosit in constructorul
unora din cele 3 tipuri de FileWriterClass.

Alt design pattern este Singleton ce este folosit in clasele Factory discutate
mai sus. Folosindu-l, nu mai trebuie sa instantiez factory-urile in Main ca
apoi sa fie date ca parametru pana ajung la metoda care le foloseste.

