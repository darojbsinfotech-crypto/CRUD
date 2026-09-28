# StudyHub – Android CRUD App (Java + Room)

A study-organizer app with three data models (**Subject**, **Task**, **Note**), each with full Create / Read / Update / Delete.

## Run it
1. Open the `StudyHub` folder in **Android Studio** (Koala or newer). Let Gradle sync.
2. Run on an emulator or a phone (Android 7.0+).
3. Build the APK: **Build > Build Bundle(s) / APK(s) > Build APK(s)**  
   Output: `app/build/outputs/apk/debug/app-debug.apk`

## Using the app
- Bottom bar switches between Subjects, Tasks and Notes.
- **Add** button = Create. Cards list = Read. Pencil = Update (asks for confirmation). Trash = Delete (asks for confirmation).

## Structure
- `data/` – Room entities, DAOs, `AppDatabase`
- `ui/` – `MainActivity`, `BaseListFragment` (generic CRUD logic), three thin fragments, `ItemAdapter`
