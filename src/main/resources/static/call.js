let localStream;

const localVideo = document.getElementById('localVideo');
const micBtn = document.getElementById('micBtn');
const videoBtn = document.getElementById('videoBtn');
const endCallBtn = document.getElementById('endCallBtn');

async function startCamera() {
  try {
    localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
    localVideo.srcObject = localStream;
  } catch(err) {
    alert('Camera or mic access denied: ' + err);
  }
}

micBtn.addEventListener('click', () => {
  if (!localStream) return;
  const audioTrack = localStream.getAudioTracks()[0];
  audioTrack.enabled = !audioTrack.enabled;
  micBtn.textContent = audioTrack.enabled ? "🎤" : "🔇";
});

videoBtn.addEventListener('click', () => {
  if (!localStream) return;
  const videoTrack = localStream.getVideoTracks()[0];
  videoTrack.enabled = !videoTrack.enabled;
  videoBtn.textContent = videoTrack.enabled ? "📹" : "📷";
});

endCallBtn.addEventListener('click', () => {
  if (!localStream) return;
  localStream.getTracks().forEach(track => track.stop());
  alert("Call ended!");
  window.location.href = 'index.html';
});

startCamera();
