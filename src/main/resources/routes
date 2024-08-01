1. **Route :** `POST /auth/register`
   - **Description :** Register a new user.
   - **Réponses :**
     - Code 200 User created
     - Code 400 Name, password, or email cannot be empty.
     - Code 403 Validation failed
     - Code 409 A user with this email already exists.
     - Code 500 Server Error

2. **Route :** `POST /auth/login`
   - **Description :** Log in an existing user.
   - **Réponses :**
     - Code 200 Successfully signed in
     - Code 400 Password or email cannot be empty.
     - Code 500 Server Error

3. **Route :** `GET /auth/me`
   - **Description :** Get informations about the logged user
   - **Réponses :**
     - Code 200 Success
     - Code 500 Server Error

4. **Route :** `POST /messages`
   - **Description :** Send messages.
   - **Réponses :**
     - Code 200 Message sent successfully
     - Code 401 Error occurred while sending message

5. **Route :** `GET /rentals`
   - **Description :** List of all rentals
   - **Réponses :**
     - Code 200 Success
     - Code 500 Server Error

6. **Route :** `GET /rentals/:id`
   - **Description :** Specific rental informations
   - **Réponses :**
     - Code 200 Success
     - Code 401 Rental not found

7. **Route :** `POST /rentals`
   - **Description :** Create a new rental
   - **Réponses :**
     - Code 200 Rental created
     - Code 400 User for owner_id not found
     - Code 500 Error creating the rental

8. **Route :** `PUT /rentals/:id`
   - **Description :** Update an existing rental
   - **Réponses :**
     - Code 200 Rental updated successfully
     - Code 500 Error updating rental 

9. **Route :** `GET /user/:id`
   - **Description :** Specific user details
   - **Réponses :**
     - Code 200 User found
     - Code 404 Not found
