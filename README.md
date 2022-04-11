Kuba Walusiak, Juliusz Kościołek 09.04.2022, Laboratorium 3

# Problem ograniczonwego bufora

## Polecenie

Dany jest bufor, do którego producent może wkładać dane, a konsument pobierać. Napisać program, który zapewni takie
działanie producenta i konsumenta, w którym zapewniona będzie własność bezpieczeństwa i żywotności.

Zrealizować program:

* przy pomocy metod wait()/notify()/signal()/await()
    * dla przypadku 1 producent/1 konsument
    * dla przypadku n1 producentów/n2 konsumentów (n1>n2, n1=n2, n1 mniej od n2)
    * wprowadzić wywołanie metody sleep() i wykonać pomiary, obserwując zachowanie producentów/konsumentów
* przy pomocy operacji P()/V() dla semafora:
    * n1=n2=1
    * n1>1, n2>1

## Wykonanie

### Producer Consumer

Stworzono klasy `Producer` i `Consumer`, które przyjmują różne implementacje interfejsu `Buffer`.

* `Producer` jest klasą produkującą pewien zasób. Jako, iż zasób sam w sobie nie ma wpływu na laboratorium, w programie
  jest to `Integer`. W rzeczywistym programie produkcja zasobu zajmowałaby pewną ilość czasu. W laboratoryjnym programie
  produkcję zastąpiono wywołaniem `sleep(10)`.
* `Consumer` jest klasą konsumującą zasób. W laboratoryjnym programie konsumpcję zastąpiono wywołaniem `sleep(20)`.

Klasy `Producer` i `Consumer` nie implementują żadnych mechanizmów synchronizujących ich działanie. Jest to w pełni
odpowiedzialność klasy `Buffer`.

### Buffer

Stworzono interfejs `Buffer` z operacjami `put()` i `get()` reprezentujący bufor o pojemności 1 łączący producentów i
konsumentów oraz jego dwie implementacje.  
Pierwsza z nich to  `BufferLock` oparty na klasach `Lock` oraz `Condition`. Synchronizacja jest realizowana za pomocą 2
zmiennych warunkowych: `isFullCondition` oraz `isEmptyConfition`
sygnalizujących odpowiednio czy bufor jest pełny czy pusty.  
Druga, nieco prostsza implementacja to `BufferSemaphore` oparty na 2 semaforach: odpowiedzialnym za sygnalizację
dostępnego miejsca w buforze, oraz za sygnalizację dostępnego zasobu.  
Testy wykazały, że obydwie implementacje gwarantują "exactly-once delivery", czyli każdy komunikat od producenta trafia
do dokładnie jednego konsumenta, dokładnie raz.

## Pomiary

Zmierzono czas wykonania dla różnej ilości konsumentów, producentów i obu typów bufora (`BufferLock`, `BufferSemaphore`).

```
Starting test for: BufferLock
Producers: 1 | Consumers: 1 | Duration 2008ms
Producers: 10 | Consumers: 10 | Duration 2050ms
Producers: 10 | Consumers: 2 | Duration 10078ms
Producers: 2 | Consumers: 10 | Duration 1022ms
Ending test for: BufferLock

Starting test for: BufferSemaphore
Producers: 1 | Consumers: 1 | Duration 1995ms
Producers: 10 | Consumers: 10 | Duration 2021ms
Producers: 10 | Consumers: 2 | Duration 10072ms
Producers: 2 | Consumers: 10 | Duration 1022ms
Ending test for: BufferSemaphore
```