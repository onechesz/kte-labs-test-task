<h1>Веб-приложение, реализованное в процессе выполнение тестового задания на позицию Junior Java-разработчика в компанию https://www.ktelabs.ru/.</h1>

<h2>О проекте</h2>

<h3>Описание</h3>

<p>Проект представляет собой RESTful-приложение, которое позволяет пациентам записываться на приём к врачам.</p>

<h2>Разработка</h2>

<h3>Технологии</h3>

<p>Согласно техническому заданию, необходимо было использовать обязательно такие вещи как PostgreSQL, Spring Boot (SOAP, REST).</p>
<p>Для того чтобы разработка была более удобной я принял решение дополнить этот стек парой полезных компонентов фреймворка (Validation, Liquibase, Web Services, Data JPA).</p>
<p>Список технологий, использованных в процессе разработки:</p>
<ul>
    <li>Java (corretto-18.0.2)</li>
    <li>Maven</li>
    <li>Spring (Validation, Web, Web Services, PostgreSQL Driver, Data JPA, Liquibase, wsdl4j, jaxb2)</li>
    <li>PostgreSQL</li>
</ul>

<h3>Процесс</h3>

<p>Приложение разрабатывалось мной в IntelliJ IDEA. Я коммитил каждый этап, а также трекал время за работой.</p>

<h2>Результаты</h2>

<h3>Сводка</h3>

<p>Ссылки:</p>
<ul>
    <li><a href="https://drive.google.com/file/d/1GtYyu9jD7VsZ-8XLqZCh0GTkVu54rpe_/view?usp=sharing">этапы разработки проекта (трекинг времени)</a></li>
    <li><a href="https://docs.google.com/document/d/1ZcsTwmx0ON2rht8v-MjEcqwxmPoGdQJQ/view?usp=sharing">оригинальное ТЗ</a></li>
</ul>

<h3>Запуск</h3>
<p>Порядок действий для запуска приложения:</p>
<ol>
    <li>Скачать проект и распаковать архив</li>
    <li>Создать базу данных в PostgreSQL и изменить (при необходимости) свойства в файле <strong>application.properties</strong></li>
    <li>Сгенерировать источники и обновить папки при помощи Maven в файле <strong>pom.xml</strong></li>
    <li>Запустить программу при помощи функции main, находящейся в классе <strong>KteLabsTestTaskApplication</strong></li>
</ol>

<p>Для получения XML-конфигурации SOAP для создания расписания, откройте в браузере: <code>корневая-ссылка-проекта/ws/schedule.wsdl</code></p>