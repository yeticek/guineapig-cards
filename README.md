# GuineaPig Cards

GuineaPig Cards is an Android application for managing and viewing card details with images. The app allows users to navigate between a list of cards, detailed card views, and fullscreen image views.

## Features

- **Home Screen**: Displays a list of cards retrieved from the database.
- **Card Details**: View detailed information about a selected card, including its name, description, and images.
- **Fullscreen Image Viewer**: View card images in fullscreen mode.

## Technologies Used

- **Programming Language**: Kotlin
- **Frameworks**: Android Jetpack (Navigation, ViewBinding, RecyclerView)
- **Build System**: Gradle

## Project Structure

- `ui/home`: Contains the `HomeFragment` for displaying the list of cards.
- `ui/details`: Contains the `CardDetailsFragment` for viewing card details.
- `ui/fullscreen`: Contains the `FullscreenImageFragment` for viewing images in fullscreen.
- `res/navigation`: Contains the navigation graph for managing app navigation.

## Navigation Flow

1. **HomeFragment**: Displays a list of cards.
2. **CardDetailsFragment**: Opens when a card is selected, showing its details and images.
3. **FullscreenImageFragment**: Opens when an image is clicked, displaying it in fullscreen.

## Key Files

### Fragments

- **`HomeFragment.kt`**:
    - Handles the home screen and displays a list of cards retrieved from the database.
    - Navigation: Links to `CardDetailsFragment` when a card is selected.

- **`CardDetailsFragment.kt`**:
    - Displays detailed information about a selected card, including its name, description, and associated images.
    - Navigation: Links to `FullscreenImageFragment` when an image is clicked.

- **`FullscreenImageFragment.kt`**:
    - Displays a single image in fullscreen mode.
    - Receives the image URI as an argument via navigation.

### Navigation

- **`mobile_navigation.xml`**:
    - Defines the app's navigation graph.
    - Key navigation paths:
        1. `HomeFragment` → `CardDetailsFragment`
        2. `CardDetailsFragment` → `FullscreenImageFragment`

### Layouts

- **`fragment_home.xml`**: Layout for the home screen displaying the list of cards.
- **`fragment_card_details.xml`**: Layout for the card details screen.
- **`fragment_fullscreen_image.xml`**: Layout for the fullscreen image viewer.

### Other

- **`.gitignore`**:
    - Ensures that the `/build` directory and other unnecessary files are excluded from version control.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.