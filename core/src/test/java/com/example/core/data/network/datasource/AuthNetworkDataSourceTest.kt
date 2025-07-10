package com.example.core.data.network.datasource

import com.example.core.ui.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class AuthNetworkDataSourceTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var authResultTask: Task<AuthResult>
    private lateinit var voidTask: Task<Void>
    private lateinit var querySnapshotTask: Task<QuerySnapshot>
    private lateinit var documentReference: DocumentReference
    private lateinit var storageReference: StorageReference

    private lateinit var authNetworkDataSource: AuthNetworkDataSource

    @Before
    fun setUp() {
        firebaseAuth = mockk(relaxed = true)
        firestore = mockk(relaxed = true)
        storage = mockk(relaxed = true)
        firebaseUser =mockk(relaxed = true)
        authResultTask =mockk(relaxed = true)
        voidTask = mockk()
        querySnapshotTask = mockk()
        documentReference = mockk(relaxed = true)
        storageReference = mockk()
        authNetworkDataSource = AuthNetworkDataSource(firebaseAuth, firestore, storage)
    }

    @Test
    fun `getUserUid should return current FirebaseUser`() {
        every { firebaseAuth.currentUser } returns firebaseUser
        val result = authNetworkDataSource.getUserUid()
        assertEquals(firebaseUser, result)
    }

    @Test
    fun `signOut should call FirebaseAuth signOut`() {
        every { firebaseAuth.signOut() } just Runs
        // Act
        authNetworkDataSource.signOut()

        // Assert
        verify { firebaseAuth.signOut() }
    }

    @Test
    fun `signInWithEmailAndPassword should call FirebaseAuth signIn method`() {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        every { firebaseAuth.signInWithEmailAndPassword(email, password)} returns authResultTask

        // Act
        val result = authNetworkDataSource.signInWithEmailAndPassword(email, password)

        // Assert
        assertEquals(authResultTask, result)

        verify {  firebaseAuth.signInWithEmailAndPassword(email, password) }
    }

    @Test
    fun `registerWithEmailAndPassword should call FirebaseAuth register method`() {
        // Arrange
        val email = "test@example.com"
        val password = "password123"
        every { firebaseAuth.createUserWithEmailAndPassword(email, password)} returns authResultTask
        // Act
        val result = authNetworkDataSource.registerWithEmailAndPassword(email, password)

        assertEquals(authResultTask, result)
        // Assert
        verify{firebaseAuth.createUserWithEmailAndPassword(email, password)}
    }

    @Test
    fun `saveUser should store user data in Firestore`() {
        // Arrange
        val userUid = "12345"
        val user = User("John Doe", "john@example.com", "1234567890", "photoUrl")

        // Mock Firestore references
        val usersCollection = mockk<CollectionReference>()
        val userDocument = mockk<DocumentReference>()
        val infoCollection = mockk<CollectionReference>()
        val profileDocument = mockk<DocumentReference>()

        // Mock Firestore behavior
        every { firestore.collection("Users") } returns usersCollection
        every { usersCollection.document(userUid) } returns userDocument
        every { userDocument.collection("info") } returns infoCollection
        every { infoCollection.document("profile") } returns profileDocument
        every { profileDocument.set(user)} returns voidTask

        // Act
        val result = authNetworkDataSource.saveUser(userUid, user)

        // Assert
        assertEquals(voidTask, result)
        verify{profileDocument.set(user)}
    }

    @Test
    fun `getProfile should retrieve user profile from Firestore`() {
        // Arrange
        val userUid = "12345"
        val usersCollection = mockk<CollectionReference>()
        val userDocument = mockk<DocumentReference>()
        val infoCollection = mockk<CollectionReference>()

        // Mock Firestore behavior
        every { firestore.collection("Users") } returns usersCollection
        every { usersCollection.document(userUid) } returns userDocument
        every { userDocument.collection("info") } returns infoCollection
        every { infoCollection.get() } returns querySnapshotTask

        // Act
        val result = authNetworkDataSource.getProfile(userUid)

        // Assert
        assertEquals(querySnapshotTask, result)
        verify{ infoCollection.get() }
    }

    @Test
    fun `saveProfile should update user profile in Firestore`() {
        // Arrange
        val userUid = "12345"
        val user = User("John Doe", "john@example.com", userUid, "1234567890", "photoUrl")
        val updates = mapOf(
            "name" to user.name,
            "email" to user.email,
            "phoneNumber" to user.phoneNumber,
            "photo" to user.photo
        )

        val usersCollection = mockk<CollectionReference>()
        val userDocument = mockk<DocumentReference>()
        val infoCollection = mockk<CollectionReference>()
        val profileDocument = mockk<DocumentReference>()
        val voidTask = mockk<Task<Void>>()

        // Mock Firestore behavior
        every { firestore.collection("Users") } returns usersCollection
        every { usersCollection.document(userUid) } returns userDocument
        every { userDocument.collection("info") } returns infoCollection
        every { infoCollection.document("profile") } returns profileDocument
        every { profileDocument.set(user) } returns voidTask
        every {
            profileDocument.update(match {
                it["name"] == "John Doe" &&
                        it["email"] == "john@example.com" &&
                        it["phoneNumber"] == "1234567890" &&
                        it["photo"] == "photoUrl"
            })
        } returns voidTask

        // Act
        val result = authNetworkDataSource.saveProfile(userUid, user)

        // Assert
        assertEquals(voidTask, result)
        verify { profileDocument.update(updates) }
    }

    @Test
    fun `uploadStorage should return StorageReference for user profile image`() {
        // Arrange
        every { firebaseAuth.currentUser } returns firebaseUser
        every { firebaseUser.uid } returns "12345"
        every { storage.reference } returns storageReference
        every { storageReference.child("profile")} returns storageReference
        every { storageReference.child("images/12345.jpeg") } returns storageReference

        // Act
        val result = authNetworkDataSource.uploadStorage()

        // Assert
        assertEquals(storageReference, result)
        verify{storageReference.child("profile")}
        verify{storageReference.child("images/12345.jpeg")}
    }

    @Test
    fun `deleteStorage should return StorageReference for given image URL`() {
        // Arrange
        val imageUrl = "https://firebase.storage.com/example.jpg"
        every { storage.getReferenceFromUrl(imageUrl) } returns storageReference

        // Act
        val result = authNetworkDataSource.deleteStorage(imageUrl)

        // Assert
        assertEquals(storageReference, result)
        verify{storage.getReferenceFromUrl(imageUrl)}
    }

    @Test
    fun `removeProfileDatabase should return Firestore DocumentReference`() {
        // Arrange
        every { firebaseAuth.currentUser} returns firebaseUser
        every { firebaseUser.uid } returns "12345"
        every { firestore.collection("Users") } returns mockk()
        every { firestore.collection("Users").document("12345") } returns mockk()
        every { firestore.collection("Users").document("12345").collection("info") } returns mockk()
        every { firestore.collection("Users").document("12345").collection("info").document("profile") }returns documentReference

        // Act
        val result = authNetworkDataSource.removeProfileDatabase()

        // Assert
        verify{firestore.collection("Users").document("12345").collection("info").document("profile")}
        assertEquals(documentReference, result)
    }
}