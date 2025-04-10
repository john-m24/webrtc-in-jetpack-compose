/*
 * Copyright 2023 Stream.IO, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.getstream.webrtc.sample.compose.ui.screens.video

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import io.getstream.webrtc.sample.compose.ui.components.VideoRenderer
import io.getstream.webrtc.sample.compose.webrtc.sessions.LocalWebRtcSessionManager

@Composable
fun VideoCallScreen() {
  val sessionManager = LocalWebRtcSessionManager.current

  LaunchedEffect(key1 = Unit) {
    sessionManager.onSessionScreenReady()
  }

  Box(
    modifier = Modifier.fillMaxSize()
  ) {
    var parentSize: IntSize by remember { mutableStateOf(IntSize(0, 0)) }

    val remoteVideoTrackState by sessionManager.remoteVideoTrackFlow.collectAsState(null)
    val remoteVideoTrack = remoteVideoTrackState

    var callMediaState by remember { mutableStateOf(CallMediaState()) }

    if (remoteVideoTrack != null) {
      VideoRenderer(
        videoTrack = remoteVideoTrack,
        modifier = Modifier
          .fillMaxSize()
          .onSizeChanged { parentSize = it }
      )
    }

    val activity = (LocalContext.current as? Activity)

    VideoCallControls(
      modifier = Modifier
        .fillMaxWidth()
        .align(Alignment.BottomCenter),
      callMediaState = callMediaState,
      onCallAction = {
        when (it) {
          CallAction.LeaveCall -> {
            sessionManager.disconnect()
            activity?.finish()
          }
        }
      }
    )
  }
}
