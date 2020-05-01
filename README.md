# job4j
# CHAT REST API
The restful service CHAT. The chat is based on four domains: Person, Role, Room, and Message.
Persons can have two roles: admin and user. Only admins have the access to all resources.
Persons could be added to the rooms, and they may send messages.
The security is based on JWT token.

Used technologies:
1) Spring boot (Data JPA, Web, Security, JWT)
2) PostgreSQL & Liquibase 
3) Jackson