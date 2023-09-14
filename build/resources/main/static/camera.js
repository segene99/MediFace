// Wait for the DOM to be fully loaded
document.addEventListener("DOMContentLoaded", function() {
    // Your code here
    var video = document.getElementById("video");
    var canvas = document.getElementById("canvas");

    document.getElementById("snap").addEventListener("click", function() {
        console.log("Snap button clicked!"); // Log here

        var context = canvas.getContext("2d");
        context.drawImage(video, 0, 0, 640, 480);

        var dataURL = canvas.toDataURL('image/png');
        console.log("Captured Data URL (first 100 chars):", dataURL.substring(0, 100));

        document.getElementById("photo").value = dataURL;
    });

    // Access the webcam
    navigator.mediaDevices.getUserMedia({ video: true })
        .then(function(stream) {
            video.srcObject = stream;
            video.play();
        })
        .catch(function(err) {
            console.error("Error accessing the camera", err);
        });
});
