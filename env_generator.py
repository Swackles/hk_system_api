import os

file = open(".env", "w")

for k, v in os.environ.items():
    file.write(k + '=' + v + "\n")

file.close()