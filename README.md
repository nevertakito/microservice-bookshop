MICROSERVICE-USER

("/user")
USER:
Get
 /books - просмотр книг в наличии
 /orders - просмотр истории заказов
 /cart - посмотреть корзину книг
 /add?title=title&author=author - добавить книгу в корзину по названию и автору
 /buy - закрыть корзину
 /cancel?id=id - отмена заказа
 /del-book?id=id - удалить книгу по id в корзине
Post:
 /reg - регистрация



ADMIN:
Get
 /all-orders - заказы всех пользователей
 /add-book - добавить книгу в магазин
 /all-user - все пользователи
 /{id} - пользователь под id 
Put
 /{id} - изменить пользователя
Delete
 /{id} - удалить пользователя
