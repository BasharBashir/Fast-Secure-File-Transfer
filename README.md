# Fast Secure File Transfer
This project presents the development of a new file transfer application that uses the TCP protocol with sliding window technology. The application is designed for client-server communication and aims to provide a fast, reliable, and secure way to transfer files. The project is divided into two parts. In the first part, a client requests a file from other connected clients and establishes peer-to-peer connections with those clients to retrieve the file. The client also determines the bandwidth available for file transfer and requests the file in chunks based on this information. The connected clients encrypt and send the requested chunks to the client, who then verifies the integrity of the received file. In the second part of the project, a client sends a file to all connected clients using peer-to-peer connections. The file is first compressed to reduce its size, and then processed and encrypted before being sent to the clients. Each client receives a different chunk of the file and immediately sends it to the other clients, while also processing and decrypting the received chunk. The sender and receiver clients verify the transferred file using a secure hashing algorithm. Overall, this project aims to provide a reliable and efficient way to transfer files in a client-server environment, utilizing the benefits of peer-to-peer communication.

# How to run 
1)To run the application, you will need to download the JavaFX library and add it to the build path of your clients and server. Start by obtaining the JavaFX library from the official Oracle website or a suitable repository.
 After downloading JavaFX, extract the contents of the library to a preferred location on your computer. Next, open the Eclipse IDE 2020 and create a new Java project for your application.
 ![1111](https://github.com/BasharBashir/Fast-Secure-File-Transfer/assets/95254660/9f69e43f-f696-4a03-b22b-5f7a0db3995a)
2)Configure the build path by right-clicking on the project in the Project Explorer, selecting "Build Path," and then "Configure Build Path." In the Libraries tab of the Configure Build Path window, click on "Add External JARs" or "Add Library" (depending on your Eclipse version).
 Navigate to the location where you extracted the JavaFX library, select the appropriate JAR files, and add them to the build path of your project. Additionally, you will need to set up the JavaFX runtime in Eclipse.
![2222](https://github.com/BasharBashir/Fast-Secure-File-Transfer/assets/95254660/d45da3bf-76d8-44d3-b548-15c1f52f9ed8)
3)Go to the Run Configurations of your Eclipse project  Access the Arguments tab. In the VM arguments section ,add the following line, replacing the path with the actual location of the JavaFX runtime on your system:
                    --module-path /path/to/javafx-sdk-VERSION/lib --add-modules javafx.controls,javafx.fxml

![3333](https://github.com/BasharBashir/Fast-Secure-File-Transfer/assets/95254660/fcebde67-73c8-4b37-9619-6ac735a1e503)


# Operating Instructions 
Server:
To enable the server to listen for incoming client connections, you need to run the server application. By running the server
![4444](https://github.com/BasharBashir/Fast-Secure-File-Transfer/assets/95254660/a5b59b36-6f89-4ef9-822e-f2300328585c)

1)The IP address of the server .

2)An information table that presents a consolidated view of all connected clients.

3)After double-clicking an IP address in the table, a list of all files existing on that IP address will be displayed.

4)The text displays the port number of the server's socket.

![5555](https://github.com/BasharBashir/Fast-Secure-File-Transfer/assets/95254660/76bd25d8-5765-4615-967c-f88e728e9f70)

Client:
To establish a connection with the server, run the client application in a manner similar to the server and connect to the server's designated port. Furthermore, when connecting with other clients, it create a new port for establishing the connections.
To connect to the server, you need the server's IP address.
![66666](https://github.com/BasharBashir/Fast-Secure-File-Transfer/assets/95254660/1a984c71-b99b-4579-aeb3-3690dbe92adb)

Client Page: 
This is the main page of the client application, where you will find two options: "Share" and "Request File." These options allow you to perform specific actions related to file sharing.

![7777](https://github.com/BasharBashir/Fast-Secure-File-Transfer/assets/95254660/5acb8769-0c5d-45e2-80b1-2b09eae87bc6)

1)Selecting the "Share" option, you can share files with other clients .

2)Choosing the "Request File" option enables you to request files from other clients .

3)The table displays all the files that exist in the client's storage.

4)Exit button.


