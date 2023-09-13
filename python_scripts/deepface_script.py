from deepface import DeepFace

def analyze_image(image_path):
    result = DeepFace.analyze(image_path, actions=['age', 'gender', 'race', 'emotion'])
    return result
