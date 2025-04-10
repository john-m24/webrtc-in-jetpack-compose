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

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import io.getstream.webrtc.sample.compose.R
import io.getstream.webrtc.sample.compose.ui.theme.Disabled
import io.getstream.webrtc.sample.compose.ui.theme.Primary

sealed class CallAction {

  object LeaveCall : CallAction()
}

data class VideoCallControlAction(
  val icon: Painter,
  val iconTint: Color,
  val background: Color,
  val callAction: CallAction
)

@Composable
fun buildDefaultCallControlActions(
  callMediaState: CallMediaState
): List<VideoCallControlAction> {
  val microphoneIcon =
    painterResource(
      id = if (callMediaState.isMicrophoneEnabled) {
        R.drawable.ic_mic_on
      } else {
        R.drawable.ic_mic_off
      }
    )

  val cameraIcon = painterResource(
    id = if (callMediaState.isCameraEnabled) {
      R.drawable.ic_videocam_on
    } else {
      R.drawable.ic_videocam_off
    }
  )

  return listOf(
    VideoCallControlAction(
      icon = painterResource(id = R.drawable.ic_call_end),
      iconTint = Color.White,
      background = Disabled,
      callAction = CallAction.LeaveCall
    )
  )
}
