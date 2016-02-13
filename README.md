Stworzyć aplikację która,będzie posiadać 2 ekrany (Activity):

1. Ekran z listą elementów.
 a) Dane dla listy mają pochodzić z dwóch różnych API (podane na końcu dokumentu) oraz przed wyświetleniem odpowiednio scalone (kolejność elementów nie jest istotna). Paginacja nie musi być obsłużona.
 b) Elementy mają być ułożone w formie siatki 2-kolumnowej. Zalecane jest użycie kontrolki RecyclerView.
 c) Kontrolka wyświetlająca ma być dodatkowo umieszczona we fragmencie, czyli powinna być zachowana hierarchia: Activity - Fragment - Kontrolka
 d) Element na liście ma posidać informacje:
    - nazwę użytkownika
    - zdjęcie / avatar
 e) W zależności od tego z jakiego API będzie pochodzić element, powinien być ustawiony różny kolor tła elementu.
 f) Po kliknięciu elementu ma otworzyć się nowy ekran ze szczegółami (Activity)
2. Activity ze szczegółami ma być prostą prezentacją wcześniej wybranego elementu z listy
 a) ma posiadać informacje:
    - nazwę użytkownika
    - zdjęcie / avatar
 b) W zależności od tego z jakiego API będzie pochodzić element, powinien być ustawiony różny kolor dla tekstu.

Wymagania ogólne:
 a) Aplikacja ma mieć możliwość obracania ekranu
 b) Podczas sprawdzania będzie uruchomiona na urządzeniu opcja Don't keep activities
 c) aplikacja nie powinna przeładowywać danych na nowo po obrocie ekranu
 d) użycie MVP, Unit testów nie jest wymagane, ale będzie dodatkowym atutem.

Endpoint’y:
 a) Daily motion: https://api.dailymotion.com/users?fields=avatar_360_url,username
 b) GitHub: https://api.github.com/users
