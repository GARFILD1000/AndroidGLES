#include <jni.h>
#include <GLES/gl.h>
#include <GLES/glext.h>
#include <stdlib.h>
#include <math.h>

#define PI 3.1415926535f

float rotate = 0;
int textureID;
int pause = 0;

static float textureCoords[] = {
	0.0f,0.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,1.0f,
	0.0f,0.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,1.0f,
	0.0f,0.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,1.0f,
	0.0f,0.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,1.0f,
	0.0f,0.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,1.0f,
	0.0f,0.0f, 0.0f,1.0f, 1.0f,0.0f, 1.0f,1.0f
};

float vertices[] = {
	-1.0f,-1.0f, 1.0f,  1.0f,-1.0f, 1.0f, -1.0f, 1.0f, 1.0f,  1.0f, 1.0f, 1.0f, // Front face
	 1.0f,-1.0f, 1.0f,  1.0f,-1.0f,-1.0f,  1.0f, 1.0f, 1.0f,  1.0f, 1.0f,-1.0f, // Right face
	 1.0f,-1.0f,-1.0f, -1.0f,-1.0f,-1.0f,  1.0f, 1.0f,-1.0f, -1.0f, 1.0f,-1.0f, // Back face
	-1.0f,-1.0f,-1.0f, -1.0f,-1.0f, 1.0f, -1.0f, 1.0f,-1.0f, -1.0f, 1.0f, 1.0f, // Left face
	-1.0f,-1.0f,-1.0f,  1.0f,-1.0f,-1.0f, -1.0f,-1.0f, 1.0f,  1.0f,-1.0f, 1.0f, // Bottom face
	-1.0f, 1.0f, 1.0f, 	1.0f, 1.0f, 1.0f, -1.0f, 1.0f,-1.0f,  1.0f, 1.0f,-1.0f  // Top face
};

int count = 36;

float normals[] = {
	 0.0f, 0.0f, 1.0f,  0.0f, 0.0f, 1.0f,  0.0f, 0.0f, 1.0f,  0.0f, 0.0f, 1.0f, // Front face
	 1.0f, 0.0f, 0.0f,	1.0f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f,  1.0f, 0.0f, 0.0f, // Right face
	 0.0f, 0.0f,-1.0f,  0.0f, 0.0f,-1.0f,  0.0f, 0.0f,-1.0f,  0.0f, 0.0f,-1.0f, // Back face
	-1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, // Left face
	 0.0f,-1.0f, 0.0f,  0.0f,-1.0f, 0.0f,  0.0f,-1.0f, 0.0f,  0.0f,-1.0f, 0.0f, // Bottom face
	 0.0f, 1.0f, 0.0f,	0.0f, 1.0f, 0.0f,  0.0f, 1.0f, 0.0f,  0.0f, 1.0f, 0.0f  // Top face
};

// Indices
GLbyte indices[] = {
	0,  1,  3,  0,  3,  2,
	4,  5,  7,  4,  7,  6, 
	8,  9,  11, 8,  11, 10, 
	12, 13, 15, 12, 15, 14, 
	16, 17, 19, 16, 19, 18, 
	20, 21, 23, 20, 23, 22
};

static void gluPerspective(float fovy, float aspect, float zNear, float zFar) {

    float ymax = zNear * (float)tan(fovy * PI / 360);
    float ymin = -ymax;
    float xmin = ymin * aspect;
    float xmax = ymax * aspect;

    glFrustumf(xmin, xmax, ymin, ymax, zNear, zFar);
}

// Init

void Java_com_jnicube_myapplication_GLView_nativeInit(JNIEnv*  env, jclass thiz) {
		float lightAmbient[] = {0.0f,0.0f,0.0f,1.0f};
	float lightDiffuse[] = {1.0f,1.0f,1.0f,1.0f};
	float lightPosition[] = {0.0f, 0.0f, 10.0f, 1.0f};

	glLightfv(GL_LIGHT0, GL_AMBIENT, lightAmbient);		
	glLightfv(GL_LIGHT0, GL_DIFFUSE, lightDiffuse);		
	glLightfv(GL_LIGHT0, GL_POSITION, lightPosition);
	glEnable(GL_LIGHTING);
	glEnable(GL_LIGHT0);

	glEnable(GL_TEXTURE_2D);					
	glShadeModel(GL_SMOOTH);
	glClearColorx(0,0,0,1);
	glEnable(GL_DEPTH_TEST);
	glEnableClientState(GL_VERTEX_ARRAY);
	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	glEnableClientState(GL_NORMAL_ARRAY);
	glFrontFace(GL_CCW);
}

// Window resize
void Java_com_jnicube_myapplication_GLView_nativeResize(JNIEnv* env, jclass thiz, jint w, jint h) {
    glViewport(0, 0, w, h);

    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    gluPerspective(45, (float)w / h, 1, 100);

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
}

// Pause/Resume
void Java_com_jnicube_myapplication_GLView_nativePause(JNIEnv* env, jclass thiz) {
	pause = !pause;
}

// Render
void Java_com_jnicube_myapplication_GLView_nativeRender(JNIEnv* env, jclass thiz) {
    glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

	if (!pause) rotate++;

	glLoadIdentity();

    glBindTexture(GL_TEXTURE_2D, textureID);

    glVertexPointer(3, GL_FLOAT, 0, vertices);
	glTexCoordPointer(2, GL_FLOAT, 0, textureCoords);
	glNormalPointer(GL_FLOAT, 0, normals);

	glTranslatef(0.0f, 0.0f, -7.f);

	glRotatef(rotate * 1.0f, 1,0,0);
	glRotatef(rotate * 0.7f, 0,1,0);
	glRotatef(rotate * 1.3f, 0,0,1);

	glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, indices);
}

// Create the texture
void Java_com_jnicube_myapplication_GLView_nativePushTexture(JNIEnv*env, jclass thiz, jintArray arr, jint w, jint h) {

	int len = w*h*4;
	char* body = (char*) (*env)->GetIntArrayElements(env, arr, 0);
	char* data = malloc(len);

	// This converts the ARGB data from Java into RGBA data OpenGL can use.
	for (int i = 0; i < len; i += 4) {
		data[i]   = body[i+2];
		data[i+1] = body[i+1];
		data[i+2] = body[i];
		data[i+3] = body[i+3];
	}
	
	glGenTextures(1, (GLuint*) &textureID);
	glBindTexture(GL_TEXTURE_2D, textureID);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, (void*)data);
}

