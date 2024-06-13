import pymysql as psql

host = "localhost"
user = "DimaTolmachev"
password = "dimaApelsinKa!4_n"
name_date_base = "digital_library"

connection = psql.connect(
    host=host,
    port=3306,
    user=user,
    password=password,
    database=name_date_base
)

with connection.cursor() as cursor:
    cursor.execute("SELECT COUNT(*) FROM book_division")
    connection.commit()

    count = cursor.fetchall()[0][0]

    for i in range(1, count + 1):
        cursor.execute(f"SELECT MIN(division_id) FROM book_division WHERE division_id > {i}")
        id_update = cursor.fetchall()[0][0]

        cursor.execute(f"UPDATE book_division SET division_id = {i} WHERE division_id = {id_update}")

        connection.commit()

    cursor.execute(f"ALTER TABLE A AUTO_INCREMENT = {count}")

connection.close()
