// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

(LISTEN_KBD)
    @SCREEN
    D=A
    @8191
    D=D+A
    @screen_ptr
    M=D         // screen_ptr = screen + 8191

    @KBD
    D=M
    @CHOOSE_COLOR
    D;JNE       // if kbd != 0 goto CHOOSE_COLOR

    // No key pressed
    @color
    M=0
    @PRINT_SCREEN
    0;JMP

    // Key pressed
    (CHOOSE_COLOR)
        @color
        M=-1

    (PRINT_SCREEN)
        @screen_ptr
        D=M
        @SCREEN
        D=D-A
        @LISTEN_KBD
        D;JLT   // if screen_ptr - SCREEN == 0 goto LISTEN_KBD

        @color
        D=M
        @screen_ptr
        A=M
        M=D     // screen position = color

        @screen_ptr
        M=M-1   // screen_ptr = i - 1
        
        @PRINT_SCREEN
        0;JMP

    @LISTEN_KBD
    D;JNE