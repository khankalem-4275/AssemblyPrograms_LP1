import xmlrpc.client

proxy = xmlrpc.client.ServerProxy("http://localhost:6789/") # very important statement
print("Connected to localhost 6789")

while True:
    print(" 1. Add two numbers ",
          " 2. Subtract two numbers ",
          " 3. Multiply two numbers ",
          " 4. Divide two numbers ",
          " 5. Exit ",
          sep= "\n")
    choice = int(input("Enter choice : "))
    if(choice == 1):
        num1 = int(input("Enter first number : "))
        num2 = int(input("Enter second number : "))
        result = proxy.add(num1,num2)
        print(f"Addition is {result}")
    elif (choice == 2):
        num1 = int(input("Enter first number : "))
        num2 = int(input("Enter second number : "))
        result = proxy.subtract(num1,num2)
        print("Subtraction is {result}")
    elif (choice == 3):
        num1 = int(input("Enter first number : "))
        num2 = int(input("Enter second number : "))
        result = proxy.multiply(num1,num2)
        print("Multiplication is {result}")
    elif (choice == 4):
        num1 = int(input("Enter first number : "))
        num2 = int(input("Enter second number : "))
        result = proxy.division(num1,num2)
        print(f"Quotient is {result[0]}\nRemainder is {result[1]}")
    elif (choice == 5):
        break
        